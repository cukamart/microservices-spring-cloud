package com.cukamart.orderservice.service;

import com.cukamart.orderservice.dto.InventoryResponse;
import com.cukamart.orderservice.dto.OrderLineItemDto;
import com.cukamart.orderservice.dto.OrderRequest;
import com.cukamart.orderservice.model.Order;
import com.cukamart.orderservice.model.OrderLineItem;
import com.cukamart.orderservice.repository.OrderRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.transaction.Transactional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final ObservationRegistry observationRegistry;

    public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder, ObservationRegistry observationRegistry) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
        this.observationRegistry = observationRegistry;
    }

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        orderRequest.getOrderLineItemDtoList().stream()
                .map(this::mapToDto)
                .forEach(order::addOrderLineItem);

        List<String> skuCodes = order.getOrderLineItems().stream()
                .map(OrderLineItem::getSkuCode)
                .toList();

        // distributed tracing micrometer
        Observation inventoryServiceObservation = Observation.createNotStarted("inventory-service-lookup",
                this.observationRegistry);
        inventoryServiceObservation.lowCardinalityKeyValue("call", "inventory-service");

        // Call inventory Service, and place order if product is in stock - synchronous request
        return inventoryServiceObservation.observe(() -> {
            List<InventoryResponse> inventoryResponses = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<InventoryResponse>>() {
                    })
//                                                          .onErrorResume(WebClientResponseException.NotFound.class, notFound -> Mono.empty())
                    .blockOptional()
                    .orElseGet(ArrayList::new);

            boolean allProductsInStock = inventoryResponses.stream().allMatch(InventoryResponse::isInStock);

            if (allProductsInStock) {
                orderRepository.save(order);
                return "Order Placed Successfully";
            } else {
                throw new IllegalArgumentException("Product is not in stock, please try again later");
            }
        });

    }

    private OrderLineItem mapToDto(OrderLineItemDto lineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(lineItemDto.getPrice());
        orderLineItem.setQuantity(lineItemDto.getQuantity());
        orderLineItem.setSkuCode(lineItemDto.getSkuCode());

        return orderLineItem;
    }
}
