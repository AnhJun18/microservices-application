package com.pa.orderservice.service;

import com.pa.orderservice.model.Order;
import com.pa.orderservice.model.OrderLineItem;
import com.pa.orderservice.payload.request.OrderLineItemRequest;
import com.pa.orderservice.payload.request.OrderRequest;
import com.pa.orderservice.payload.response.InventoryResponse;
import com.pa.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    WebClient.Builder webClientBuilder;

    public void createOrder(OrderRequest orderRequest) throws IllegalAccessException {

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .build();
        List<OrderLineItem> allItems = orderRequest.getOrderLineItemRequestList()
                .stream().map(this::mapToOrderLine).collect(Collectors.toList());
        order.setOrderLineItemsList(allItems);
        List<String> params = order.getOrderLineItemsList()
                .stream().map(OrderLineItem::getSkuCode)
                .collect(Collectors.toList());
        //Check stock
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get().uri("http://inventory-service/api/inventory",
                UriBuilder -> UriBuilder.queryParam("skuCode", params).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean AllIsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::getIsInStock);

        if (AllIsInStock) {
            orderRepository.save(order);
        } else
            throw new IllegalAccessException("Product was sold");
    }

    private OrderLineItem mapToOrderLine(OrderLineItemRequest item) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(item.getPrice());
        orderLineItem.setQuantity(item.getQuantity());
        orderLineItem.setSkuCode(item.getSkuCode());
        return orderLineItem;
    }
}
