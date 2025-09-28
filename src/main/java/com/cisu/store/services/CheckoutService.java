package com.cisu.store.services;

import com.cisu.store.dtos.CheckoutRequest;
import com.cisu.store.dtos.CheckoutResponse;
import com.cisu.store.entities.Order;
import com.cisu.store.exceptions.CartEmptyException;
import com.cisu.store.exceptions.CartNotFoundException;
import com.cisu.store.repositories.CartRepository;
import com.cisu.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;

    public CheckoutResponse checkout(CheckoutRequest request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()) {
           throw new CartEmptyException();
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return new CheckoutResponse(order.getId());
    }
}
