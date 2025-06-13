package com.cisu.store.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CartDto {
    private UUID id;
    // new ArrayList<>() is the default value
    // if we don't add this we get null to the response
    private List<CartItemDto> items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
