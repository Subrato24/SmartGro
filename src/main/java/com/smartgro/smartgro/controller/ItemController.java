package com.smartgro.smartgro.controller;

import com.smartgro.smartgro.dto.ItemDto;
import com.smartgro.smartgro.entity.Item;
import com.smartgro.smartgro.repository.ItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://localhost:5173")
public class ItemController {
    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @PostMapping
    public Item createItem(@RequestBody ItemDto request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setBrand(request.getBrand());
        item.setUnit(request.getUnit());
        return itemRepository.save(item);
    }

    @GetMapping
    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }
}
