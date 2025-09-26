package com.smartgro.smartgro.controller;

import com.smartgro.smartgro.dto.ShopDto;
import com.smartgro.smartgro.entity.Shop;
import com.smartgro.smartgro.mapper.ShopMapper;
import com.smartgro.smartgro.repository.ShopRepository;
import com.smartgro.smartgro.service.ShopService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
@CrossOrigin(origins = "http://localhost:5173")
public class ShopController {
    private final ShopRepository shopRepository;
    private final ShopService shopService;

    public ShopController(ShopRepository shopRepository, ShopService shopService) {
        this.shopRepository = shopRepository;
        this.shopService = shopService;
    }

    @PostMapping()
    public Shop createShop(@RequestBody ShopDto shopDto) {
        ShopDto shop = new ShopDto();
        shop.setName(shopDto.getName());
        shop.setAddress(shopDto.getAddress());
        return shopRepository.save(ShopMapper.toEntity(shop));
    }

    @GetMapping()
    public List<Shop> showShops(){
        return shopRepository.findAll();
    }

    @PutMapping("/update/{id}")
    public void updateShop(@PathVariable Long id, @RequestBody ShopDto shop){
        try{
            shopService.updateShop(id,shop);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
