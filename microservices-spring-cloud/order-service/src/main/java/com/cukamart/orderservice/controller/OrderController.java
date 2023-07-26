package com.cukamart.orderservice.controller;

import com.cukamart.orderservice.dto.OrderRequest;
import com.cukamart.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod") // resilience4j.circuitbreaker
    @TimeLimiter(name = "inventory") // resilience4j.timelimiter (CompletableFuture)
    @Retry(name = "inventory") // resilience4j.retry
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
    }

    // CompletableFuture - ak pouzijeme Timelimiter (Resilience4j v application.properties nastavime kolko cakame sekund na response)
    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(() -> "Something went wrong");
    }
}
