package com.cisu.store.controllers;

import com.cisu.store.entities.User;
import com.cisu.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;


    @RequestMapping("/users")
    @GetMapping("/users")
    public Iterable<User> getAllUser() {
        return userRepository.findAll();
    }
}
