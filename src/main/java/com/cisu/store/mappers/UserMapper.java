package com.cisu.store.mappers;

import com.cisu.store.dtos.UserDto;
import com.cisu.store.entities.User;
import org.mapstruct.Mapper;

// componentModel = "spring" => can create Bean of this Map run time
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
