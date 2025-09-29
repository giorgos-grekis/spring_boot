package com.cisu.store.mappers;

import com.cisu.store.dtos.OrderDto;
import com.cisu.store.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
