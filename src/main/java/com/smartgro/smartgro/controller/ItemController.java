package com.smartgro.smartgro.controller;

import com.smartgro.smartgro.dto.ItemDto;
import com.smartgro.smartgro.entity.Item;
import com.smartgro.smartgro.mapper.ItemMapper;
import com.smartgro.smartgro.repository.ItemRepository;
import com.smartgro.smartgro.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://localhost:5173")
public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    public ItemController(ItemRepository itemRepository, ItemService itemService) {
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }

    @PostMapping
    public Item createItem(@RequestBody ItemDto request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setBrand(request.getBrand());
        return itemRepository.save(item);
    }

    @GetMapping
    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable Long id, @RequestBody ItemDto itemDto) {
        try {
            Item updatedItem = itemService.updateItem(id, itemDto); // return the updated entity
            return ResponseEntity.ok(ItemMapper.toDto(updatedItem)); // return updated data to frontend
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
