package com.smartgro.smartgro.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ShoppingResponseDto {
    private Long id;
    private String userName;
    private String shopName;
    private String itemName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal total;
    private LocalDateTime createdAt;

    public ShoppingResponseDto(
            Long id,
            String userName,
            String shopName,
            String itemName,
            Integer quantity,
            BigDecimal price,
            BigDecimal total,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userName = userName;
        this.shopName = shopName;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
