package com.cukamart.orderservice.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "t_order_line_item")
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    public OrderLineItem() {
    }

    public OrderLineItem(Long id, String skuCode, BigDecimal price, Integer quantity, Order order) {
        this.id = id;
        this.skuCode = skuCode;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    // - https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderLineItem )) return false;
        return id != null && id.equals(((OrderLineItem) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
