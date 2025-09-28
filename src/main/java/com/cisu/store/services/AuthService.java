package com.cisu.store.services;

import com.cisu.store.entities.User;
import com.cisu.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        var authetication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authetication.getPrincipal();

        return userRepository.findById(userId).orElse(null);
    }

}
