package com.smartgro.smartgro.service;

import com.smartgro.smartgro.dto.ShoppingRequestDto;
import com.smartgro.smartgro.dto.ShoppingResponseDto;
import com.smartgro.smartgro.entity.Item;
import com.smartgro.smartgro.entity.Shop;
import com.smartgro.smartgro.entity.Shopping;
import com.smartgro.smartgro.entity.User;
import com.smartgro.smartgro.repository.ItemRepository;
import com.smartgro.smartgro.repository.ShopRepository;
import com.smartgro.smartgro.repository.ShoppingRepository;
import com.smartgro.smartgro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingService {

    private final ShoppingRepository shoppingRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ItemRepository itemRepository;

    public ShoppingService(ShoppingRepository shoppingRepository, UserRepository userRepository, ShopRepository shopRepository, ItemRepository itemRepository) {
        this.shoppingRepository = shoppingRepository;
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
        this.itemRepository = itemRepository;
    }

    public ShoppingResponseDto addShopping(ShoppingRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Shop shop = shopRepository.findById(dto.getShopId())
                .orElseThrow(() -> new RuntimeException("Shop not found"));
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Shopping shopping = new Shopping();
        shopping.setUser(user);
        shopping.setShop(shop);
        shopping.setItem(item);
        shopping.setQuantity(dto.getQuantity());
        shopping.setPrice(dto.getPrice());

        BigDecimal total = dto.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
        shopping.setTotal(total);

        shoppingRepository.save(shopping);

        return new ShoppingResponseDto(
                shopping.getId(),
                user.getName(),
                shop.getName(),
                item.getName(),
                shopping.getQuantity(),
                shopping.getPrice(),
                shopping.getTotal(),
                shopping.getCreatedAt()
        );
    }

    public List<ShoppingResponseDto> getUserShopping(Long userId) {
        return shoppingRepository.findByUserId(userId);
    }

    public List<Object[]> getSpendingByDate(Long userId) {
        return shoppingRepository.getSpendingByDate(userId);
    }

    public List<Object[]> getSpendingByShop(Long userId) {
        return shoppingRepository.getSpendingByShop(userId);
    }

    @Transactional
    public ShoppingResponseDto updateShopping(Long shoppingId, int quantity, BigDecimal price) {
        Shopping shopping = shoppingRepository.findById(shoppingId)
                .orElseThrow(() -> new RuntimeException("Shopping record not found"));

        shopping.setQuantity(quantity);
        shopping.setPrice(price);
        BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));
        shopping.setTotal(total);

        shoppingRepository.save(shopping);

        return new ShoppingResponseDto(
                shopping.getId(),
                shopping.getUser().getName(),
                shopping.getShop().getName(),
                shopping.getItem().getName(),
                shopping.getQuantity(),
                shopping.getPrice(),
                shopping.getTotal(),
                shopping.getCreatedAt()
        );
    }

    // ✅ Delete shopping
    public void deleteShopping(Long shoppingId) {
        if (!shoppingRepository.existsById(shoppingId)) {
            throw new RuntimeException("Shopping record not found");
        }
        shoppingRepository.deleteById(shoppingId);
    }

    // ✅ Check exists by id
    public boolean existsById(Long shoppingId) {
        return shoppingRepository.existsById(shoppingId);
    }
}
