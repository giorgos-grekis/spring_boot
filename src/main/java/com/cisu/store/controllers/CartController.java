package com.cisu.store.controllers;

import com.cisu.store.dtos.CartDto;
import com.cisu.store.entities.Cart;
import com.cisu.store.mappers.CartMapper;
import com.cisu.store.repositories.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder
    ) {
        var cart = new Cart();
        cartRepository.save(cart);

        var cartDto = cartMapper.toDto(cart);
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

}
