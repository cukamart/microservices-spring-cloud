package com.cukamart.orderservice.service;

import com.cukamart.orderservice.dto.InventoryResponse;
import com.cukamart.orderservice.dto.OrderLineItemDto;
import com.cukamart.orderservice.dto.OrderRequest;
import com.cukamart.orderservice.model.Order;
import com.cukamart.orderservice.model.OrderLineItem;
import com.cukamart.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
    }

    @Transactional
    public void placeOrder(OrderRequest orderRequest) throws IllegalAccessException {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        orderRequest.getOrderLineItemDtoList().stream()
                .map(this::mapToDto)
                .forEach(order::addOrderLineItem);

        List<String> skuCodes = order.getOrderLineItems().stream()
                .map(OrderLineItem::getSkuCode)
                .toList();

        // Call inventory Service, and place order if product is in stock - synchronous request
        // todo make it environment independent -.uri -> uriBuilder
        List<InventoryResponse> inventoryResponses = webClientBuilder.build().get()
                                                          .uri("http://inventory-service/api/inventory",
                                                                  uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                                                          .accept(MediaType.APPLICATION_JSON)
                                                          .retrieve()
                                                          .bodyToMono(new ParameterizedTypeReference<List<InventoryResponse>>() {})
//                                                          .onErrorResume(WebClientResponseException.NotFound.class, notFound -> Mono.empty())
                                                          .blockOptional()
                                                          .orElseThrow(() -> new IllegalAccessException("Product is not in stock, please try again later."));

        boolean allProductsInStock = inventoryResponses.stream().allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalAccessException("Product is not in stock, please try again later.");
        }

    }

    private OrderLineItem mapToDto(OrderLineItemDto lineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(lineItemDto.getPrice());
        orderLineItem.setQuantity(lineItemDto.getQuantity());
        orderLineItem.setSkuCode(lineItemDto.getSkuCode());

        return orderLineItem;
    }
}
