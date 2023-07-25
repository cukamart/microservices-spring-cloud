package com.cukamart.orderservice.controller;

import com.cukamart.orderservice.dto.OrderRequest;
import com.cukamart.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            orderService.placeOrder(orderRequest);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return "Order Placed Successfully";
    }
}
