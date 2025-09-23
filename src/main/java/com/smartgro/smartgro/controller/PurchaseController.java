package com.smartgro.smartgro.controller;

import com.smartgro.smartgro.dto.PurchaseDto;
import com.smartgro.smartgro.entity.Purchase;
import com.smartgro.smartgro.entity.User;
import com.smartgro.smartgro.repository.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.Instant;

public class PurchaseController {
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ShopRepository shopRepository;
    private final ShoppingSessionRepository sessionRepository;

    public PurchaseController(PurchaseRepository purchaseRepository, UserRepository userRepository, ItemRepository itemRepository, ShopRepository shopRepository, ShoppingSessionRepository sessionRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.shopRepository = shopRepository;
        this.sessionRepository = sessionRepository;
    }

    @PostMapping
    public Purchase createPurchase(@RequestBody PurchaseDto purchaseDto,
                                   @AuthenticationPrincipal(expression = "username") String email) {
        // Get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setItem(itemRepository.findById(purchaseDto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found")));

        if (purchaseDto.getShopId() != null) {
            purchase.setShop(shopRepository.findById(purchaseDto.getShopId())
                    .orElseThrow(() -> new RuntimeException("Shop not found")));
        }
        if (purchaseDto.getSessionId() != null) {
            purchase.setSession(sessionRepository.findById(purchaseDto.getSessionId())
                    .orElseThrow(() -> new RuntimeException("Session not found")));
        }

        purchase.setPrice(purchaseDto.getPrice());
        purchase.setQuantity(purchaseDto.getQuantity() != null ? purchaseDto.getQuantity() : BigDecimal.ONE);
        purchase.setPurchasedAt(purchaseDto.getPurchasedAt() != null ? purchaseDto.getPurchasedAt() : Instant.now());

        return purchaseRepository.save(purchase);
    }
}
