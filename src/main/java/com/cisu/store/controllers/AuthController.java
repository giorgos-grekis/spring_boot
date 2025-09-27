package com.cisu.store.controllers;

import com.cisu.store.dtos.JwtResponse;
import com.cisu.store.dtos.LoginRequest;
import com.cisu.store.dtos.UserDto;
import com.cisu.store.mappers.UserMapper;
import com.cisu.store.repositories.UserRepository;
import com.cisu.store.services.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
           @Valid @RequestBody LoginRequest requestBody
            ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestBody.getEmail(),
                        requestBody.getPassword()
                )
        );

        var token = jwtService.generateToken(requestBody.getEmail());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/validate")
    public boolean validate(@RequestHeader("Authorization") String authHeader) {
        System.out.println("validated called");
       var token = authHeader.replace("Bearer ", "");
       return jwtService.validateToken(token);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
       var authetication = SecurityContextHolder.getContext().getAuthentication();
       var email = (String) authetication.getPrincipal();

       var user = userRepository.findByEmail(email).orElse(null);
       if (user == null) {
           return ResponseEntity.notFound().build();
       }

       var userDto = userMapper.toDto(user);

       return ResponseEntity.ok(userDto);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
