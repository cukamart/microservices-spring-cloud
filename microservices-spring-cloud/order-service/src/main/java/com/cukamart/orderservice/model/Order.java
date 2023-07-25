package com.cukamart.orderservice.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="t_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;

    // - https://stackoverflow.com/a/51055434/4053082 dont use JoinColumn
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    public Order() {
    }

    public Order(Long id, String orderNumber, List<OrderLineItem> orderLineItems) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderLineItems = orderLineItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public void addOrderLineItem(OrderLineItem orderLineItem) {
        orderLineItems.add(orderLineItem);
        orderLineItem.setOrder(this);
    }

    public void removeComment(OrderLineItem orderLineItem) {
        orderLineItems.remove(orderLineItem);
        orderLineItem.setOrder(null);
    }
}
