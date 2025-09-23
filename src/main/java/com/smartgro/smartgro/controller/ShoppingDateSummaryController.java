package com.smartgro.smartgro.controller;

import com.smartgro.smartgro.dto.ShoppingDateSummaryDto;
import com.smartgro.smartgro.entity.ShoppingDateSummary;
import com.smartgro.smartgro.service.ShoppingDateSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shopping")
public class ShoppingDateSummaryController {
    private final ShoppingDateSummaryService service;

    public ShoppingDateSummaryController(ShoppingDateSummaryService service) {
        this.service = service;
    }

    @PostMapping("/summary/save")
    public ResponseEntity<ShoppingDateSummary> saveSummary(@RequestBody ShoppingDateSummaryDto dto) {
        // dto now includes userId
        ShoppingDateSummary saved = service.saveSummary(dto);
        return ResponseEntity.ok(saved);
    }

}
