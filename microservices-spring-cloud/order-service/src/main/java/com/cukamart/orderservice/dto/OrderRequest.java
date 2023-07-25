package com.cukamart.orderservice.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
    private List<OrderLineItemDto> orderLineItemDtoList = new ArrayList<>();

    public OrderRequest() {
    }

    public OrderRequest(List<OrderLineItemDto> orderLineItemDtoList) {
        this.orderLineItemDtoList = orderLineItemDtoList;
    }

    public List<OrderLineItemDto> getOrderLineItemDtoList() {
        return orderLineItemDtoList;
    }

    public void setOrderLineItemDtoList(List<OrderLineItemDto> orderLineItemDtoList) {
        this.orderLineItemDtoList = orderLineItemDtoList;
    }
}
