package com.cisu.store.controllers;

import com.cisu.store.dtos.RegisterUserRequest;
import com.cisu.store.dtos.UpdateUserRequest;
import com.cisu.store.dtos.UserDto;
import com.cisu.store.mappers.UserMapper;
import com.cisu.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUser(
            @RequestHeader(name = "x-auth-token", required = false) String authToken,
           @RequestParam(
                   name = "sort",
                   required = false,
                   defaultValue = "") String sortBy
    ) {
        System.out.println(authToken);

       if (!Set.of("name", "email").contains(sortBy))
           sortBy = "name";


        return userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }

//        var userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
//        return new ResponseEntity<>(user, HttpStatus.OK);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder) {

        var user = userMapper.toEntity(request);
        user = userRepository.save(user);

        var userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserRequest request){

        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userMapper.update(request, user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    ) {
        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

}
