package com.cisu.store.services;

import com.cisu.store.dtos.OrderDto;
import com.cisu.store.mappers.OrderMapper;
import com.cisu.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrder() {
        var user = authService.getCurrentUser();
        var orders = orderRepository.getAllOrdersByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }
}
