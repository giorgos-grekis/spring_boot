package com.cisu.store.mappers;

import com.cisu.store.dtos.RegisterUserRequest;
import com.cisu.store.dtos.UpdateUserRequest;
import com.cisu.store.dtos.UserDto;
import com.cisu.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

// componentModel = "spring" => can create Bean of this Map run time
@Mapper(componentModel = "spring")
public interface UserMapper {
//    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    UserDto toDto(User user);

    User toEntity(RegisterUserRequest request);

    void update(UpdateUserRequest request, @MappingTarget User user);
}
