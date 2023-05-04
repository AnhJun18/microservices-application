package com.pa.orderservice.service;

import com.pa.orderservice.model.Order;
import com.pa.orderservice.model.OrderLineItem;
import com.pa.orderservice.payload.request.OrderLineItemRequest;
import com.pa.orderservice.payload.request.OrderRequest;
import com.pa.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@Transactional
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    public void createOrder(OrderRequest orderRequest){

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .build();
        List<OrderLineItem> allItems = orderRequest.getOrderLineItemRequestList()
                .stream().map(this::mapToOrderLine).collect(Collectors.toList());
        order.setOrderLineItemsList(allItems);
        orderRepository.save(order);
    }

    private OrderLineItem mapToOrderLine(OrderLineItemRequest item) {
        OrderLineItem orderLineItem= new OrderLineItem();
        orderLineItem.setPrice(item.getPrice());
        orderLineItem.setQuantity(item.getQuantity());
        orderLineItem.setSkuCode(item.getSkuCode());
        return  orderLineItem;
    }
}
