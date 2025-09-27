package com.cisu.store.services;

import com.cisu.store.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.jwt.secret}")
    private String secret;

    final long MILLISECONDS_IN_SECOND = 1000L;

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
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateAccessToken(User user) {
        final long EXPIRATION_ACCESS_TOKEN_IN_SECONDS = 300L; // 5 min
        final long tokenExpiration = MILLISECONDS_IN_SECOND * EXPIRATION_ACCESS_TOKEN_IN_SECONDS;

        return generateToken(user, tokenExpiration);
    }

    public String generateRefreshToken(User user) {
        final long EXPIRATION_REFRESH_TOKEN_IN_SECONDS = 604800; // 7 days
        final long tokenExpiration = MILLISECONDS_IN_SECOND * EXPIRATION_REFRESH_TOKEN_IN_SECONDS;

        return generateToken(user, tokenExpiration);
    }

    private String generateToken(User user, long tokenExpiration) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email: ", user.getEmail())
                .claim("name", user.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    /**
     * Check if the token is valid (not expired and structurally sound).
     * @param token The JWT string to validate.
     * @return {@code true} if the token is valid and not expired; {@code false} otherwise,
     */
    public boolean validateToken(String token) {
        try {
            var claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        }
        catch (JwtException e) {
            return false;
        }
    }


    public Long getUserIdFromToken(String token) {
       return Long.valueOf(getClaims(token).getSubject());
    }
}
