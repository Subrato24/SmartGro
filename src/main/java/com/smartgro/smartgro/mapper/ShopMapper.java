package com.smartgro.smartgro.mapper;

import com.smartgro.smartgro.dto.ShopDto;
import com.smartgro.smartgro.entity.Shop;

public class ShopMapper {
    public static ShopDto toDto(Shop shop) {
        if (shop == null) return null;

        ShopDto dto = new ShopDto();
        dto.setName(shop.getName());
        dto.setAddress(shop.getAddress());
        return dto;
    }

    // Convert DTO -> Entity
    public static Shop toEntity(ShopDto dto) {
        if (dto == null) return null;

        Shop shop = new Shop();
        shop.setName(dto.getName());
        shop.setAddress(dto.getAddress());

        return shop;
    }
}
