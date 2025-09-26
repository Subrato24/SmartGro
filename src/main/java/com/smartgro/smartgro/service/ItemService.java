package com.smartgro.smartgro.service;

import com.smartgro.smartgro.dto.ItemDto;
import com.smartgro.smartgro.entity.Item;
import com.smartgro.smartgro.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public Item updateItem(Long id, ItemDto itemDto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        item.setName(itemDto.getName());
        item.setBrand(itemDto.getBrand());

        return itemRepository.save(item); // return updated entity
    }
}
