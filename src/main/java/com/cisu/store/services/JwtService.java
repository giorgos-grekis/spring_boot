package com.cisu.store.services;

import com.cisu.store.config.JwtConfig;
import com.cisu.store.entities.Role;
import com.cisu.store.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class JwtService {
    private final JwtConfig jwtConfig;
    final long MILLISECONDS_IN_SECOND = 1000L;

    public Jwt parseToken(String token) {
        try {
            var claims = getClaims(token);
            return new Jwt(claims, jwtConfig.getSecretKey());
        } catch (JwtException e) {
            return null;
        }
    }

    /**
     * Parses the signed JWT and extracts its claims payload.
     *
     * @param token The signed JWT string.
     * @return The {@code Claims} payload contained within the token.
     * @throws JwtException If the token is not a valid signed JWT (e.g., signature fails,
     * malformed token).
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Jwt generateAccessToken(User user) {
        final long tokenExpiration = MILLISECONDS_IN_SECOND * jwtConfig.getAccessTokenExpiration();

        return generateToken(user, tokenExpiration);
    }

    public Jwt generateRefreshToken(User user) {
        final long tokenExpiration = MILLISECONDS_IN_SECOND * jwtConfig.getRefreshTokenExpiration();

        return generateToken(user, tokenExpiration);
    }

    private Jwt generateToken(User user, long tokenExpiration) {
       var claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("email", user.getEmail())
                .add("name", user.getName())
                .add("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .build();

       return new Jwt(claims, jwtConfig.getSecretKey());
    }
}
