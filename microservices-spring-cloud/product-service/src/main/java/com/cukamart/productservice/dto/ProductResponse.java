package com.cukamart.productservice.dto;

import java.math.BigDecimal;

public class ProductResponse {

    private String id;
    private String productName;
    private String description;
    private BigDecimal price;

    public ProductResponse() {
    }

    public ProductResponse(String id, String productName, String description, BigDecimal price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
