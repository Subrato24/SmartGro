package com.smartgro.smartgro.dto;

import java.math.BigDecimal;

public class SuggestionDTO {
    private Long itemId;
    private String itemName;
    private Long shopId;
    private String shopName;
    private BigDecimal bestAvgPrice;
    private BigDecimal userAvgPrice;
    private BigDecimal expectedMonthlyQty;
    private BigDecimal estimatedMonthlySaving;

    public SuggestionDTO(Long itemId, String itemName, Long shopId, String shopName,
                         BigDecimal bestAvgPrice, BigDecimal userAvgPrice,
                         BigDecimal expectedMonthlyQty, BigDecimal estimatedMonthlySaving) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.shopId = shopId;
        this.shopName = shopName;
        this.bestAvgPrice = bestAvgPrice;
        this.userAvgPrice = userAvgPrice;
        this.expectedMonthlyQty = expectedMonthlyQty;
        this.estimatedMonthlySaving = estimatedMonthlySaving;
    }

    // Getters & Setters
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public Long getShopId() { return shopId; }
    public void setShopId(Long shopId) { this.shopId = shopId; }

    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }

    public BigDecimal getBestAvgPrice() { return bestAvgPrice; }
    public void setBestAvgPrice(BigDecimal bestAvgPrice) { this.bestAvgPrice = bestAvgPrice; }

    public BigDecimal getUserAvgPrice() { return userAvgPrice; }
    public void setUserAvgPrice(BigDecimal userAvgPrice) { this.userAvgPrice = userAvgPrice; }

    public BigDecimal getEstimatedMonthlySaving() { return estimatedMonthlySaving; }
    public void setEstimatedMonthlySaving(BigDecimal estimatedMonthlySaving) { this.estimatedMonthlySaving = estimatedMonthlySaving; }

}
