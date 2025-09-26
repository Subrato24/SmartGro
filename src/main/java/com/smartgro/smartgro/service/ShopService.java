package com.smartgro.smartgro.service;

import com.smartgro.smartgro.dto.ShopDto;
import com.smartgro.smartgro.entity.Shop;
import com.smartgro.smartgro.repository.ShopRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopService {
    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Transactional
    public void updateShop(Long id, ShopDto shopDto){
        Shop shop = shopRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Shop not found"));

        shop.setName(shopDto.getName());
        shop.setAddress(shopDto.getAddress());

        shopRepository.save(shop);
    }

}
