package com.cukamart.productservice.dto;

import java.math.BigDecimal;

public class ProductRequest {

    private String productName;
    private String description;
    private BigDecimal price;

    public ProductRequest() {
    }

    public ProductRequest(String productName, String description, BigDecimal price) {
        this.productName = productName;
        this.description = description;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
