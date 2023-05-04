package com.pa.orderservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemRequest {
    private  String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
