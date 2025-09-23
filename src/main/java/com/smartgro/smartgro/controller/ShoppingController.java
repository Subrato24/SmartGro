package com.smartgro.smartgro.controller;

import com.smartgro.smartgro.dto.ShoppingDateSummaryDto;
import com.smartgro.smartgro.dto.ShoppingRequestDto;
import com.smartgro.smartgro.dto.ShoppingResponseDto;
import com.smartgro.smartgro.entity.ShoppingDateSummary;
import com.smartgro.smartgro.service.ShoppingDateSummaryService;
import com.smartgro.smartgro.service.ShoppingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping")
@CrossOrigin(origins = "http://localhost:5173")
public class ShoppingController {
    private final ShoppingService shoppingService;
    private final ShoppingDateSummaryService summaryService;

    public ShoppingController(ShoppingService shoppingService, ShoppingDateSummaryService summaryService) {
        this.shoppingService = shoppingService;
        this.summaryService = summaryService;
    }

    // Add shopping
    @PostMapping
    public ShoppingResponseDto addShopping(@RequestBody ShoppingRequestDto dto) {
        return shoppingService.addShopping(dto);
    }

    // Get shopping by user
    @GetMapping("/user/{userId}")
    public List<ShoppingResponseDto> getUserShopping(@PathVariable Long userId) {
        return shoppingService.getUserShopping(userId);
    }

    // Get spending by date
    @GetMapping("/summary/date/{userId}")
    public List<Object[]> getSpendingByDate(@PathVariable Long userId) {
        return shoppingService.getSpendingByDate(userId);
    }

    // Get spending by shop
    @GetMapping("/summary/shop/{userId}")
    public List<Object[]> getSpendingByShop(@PathVariable Long userId) {
        return shoppingService.getSpendingByShop(userId);
    }

    // Update shopping
    @PutMapping("/update/{id}")
    public ResponseEntity<ShoppingResponseDto> updateShopping(
            @PathVariable Long id,
            @RequestBody ShoppingRequestDto dto) {
        ShoppingResponseDto updated = shoppingService.updateShopping(id, dto.getQuantity(), dto.getPrice());
        return ResponseEntity.ok(updated);
    }

    // Delete shopping
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShopping(@PathVariable Long id) {
        if (!shoppingService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        shoppingService.deleteShopping(id);
        return ResponseEntity.ok("Item deleted successfully");
    }

//    // Save summary (date + shop + total + userId)
//    @PostMapping("/summary/save")
//    public ResponseEntity<ShoppingDateSummary> saveSummary(@RequestBody ShoppingDateSummaryDto dto) {
//        ShoppingDateSummary saved = summaryService.saveSummary(dto);
//        return ResponseEntity.ok(saved);
//    }
}
