package com.cisu.store.mappers;

import com.cisu.store.dtos.CartDto;
import com.cisu.store.entities.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto toDto(Cart cart);
}
