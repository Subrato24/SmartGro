package com.smartgro.smartgro.service;

import com.smartgro.smartgro.dto.SuggestionDTO;
import com.smartgro.smartgro.repository.ItemRepository;
import com.smartgro.smartgro.repository.PurchaseRepository;
import com.smartgro.smartgro.repository.ShopRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SuggestionService {
    private final PurchaseRepository purchaseRepo;
    private final ItemRepository itemRepo;
    private final ShopRepository shopRepo;

    public SuggestionService(PurchaseRepository purchaseRepo, ItemRepository itemRepo, ShopRepository shopRepo) {
        this.purchaseRepo = purchaseRepo;
        this.itemRepo = itemRepo;
        this.shopRepo = shopRepo;
    }

    @Transactional(readOnly = true)
    public List<SuggestionDTO> suggestForUser(Long userId, int topN) {
        var top = purchaseRepo.topItemsForUserLast3Months(userId, topN);

        // Map itemId -> user stats
        Map<Long, UserItemStats> userStats = new HashMap<>();
        for (var row : top) {
            Long itemId = ((Number) row.get("itemId")).longValue();
            BigDecimal qty = new BigDecimal(row.get("totalQty").toString());
            BigDecimal userAvg = new BigDecimal(row.get("userAvgPrice").toString());
            userStats.put(itemId, new UserItemStats(qty, userAvg));
        }

        var best = purchaseRepo.bestShopPerItemLast3Months(new ArrayList<>(userStats.keySet()));

        // Build DTOs
        List<SuggestionDTO> results = new ArrayList<>();
        for (var row : best) {
            Long itemId = ((Number) row.get("itemId")).longValue();
            Long shopId = row.get("shopId") == null ? null : ((Number) row.get("shopId")).longValue();
            BigDecimal bestAvg = new BigDecimal(row.get("avgPrice").toString());

            var item = itemRepo.findById(itemId).orElseThrow();
            var shop = shopId == null ? null : shopRepo.findById(shopId).orElse(null);

            var stats = userStats.get(itemId);
            BigDecimal expectedMonthlyQty =
                    stats.totalQty().divide(new BigDecimal("3"), 3, RoundingMode.HALF_UP); // qty per month
            BigDecimal savingPerUnit = stats.userAvg().subtract(bestAvg);
            BigDecimal estimatedMonthlySaving = savingPerUnit.multiply(expectedMonthlyQty).max(BigDecimal.ZERO);

            results.add(new SuggestionDTO(
                    item.getId(), item.getName(),
                    shop == null ? null : shop.getId(),
                    shop == null ? "Unknown" : shop.getName(),
                    bestAvg, stats.userAvg(),
                    expectedMonthlyQty, estimatedMonthlySaving
            ));
        }
        // Sort by estimated saving desc
        results.sort(Comparator.comparing(SuggestionDTO::getEstimatedMonthlySaving).reversed());

        return results;
    }

    public record UserItemStats(BigDecimal totalQty, BigDecimal userAvg) {}
}
