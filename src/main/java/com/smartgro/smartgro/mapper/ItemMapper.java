package com.smartgro.smartgro.mapper;

import com.smartgro.smartgro.dto.ItemDto;
import com.smartgro.smartgro.entity.Item;

public class ItemMapper {

    // Convert DTO to Entity
    public static Item toEntity(ItemDto dto) {
        if (dto == null) return null;

        Item item = new Item();
        item.setName(dto.getName());
        item.setBrand(dto.getBrand());
        return item;
    }

    // Convert Entity to DTO
    public static ItemDto toDto(Item item) {
        if (item == null) return null;

        ItemDto dto = new ItemDto();
        dto.setName(item.getName());
        dto.setBrand(item.getBrand());
        return dto;
    }
}
