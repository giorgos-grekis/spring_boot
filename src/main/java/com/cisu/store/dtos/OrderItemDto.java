package com.cisu.store.dtos;

import com.cisu.store.entities.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private OrderProductDto product;
    private int quantity;
    private BigDecimal totalPrice;
}
