package com.cisu.store.services;

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

    public String generateToken(String email) {
        /**
         * The token expiration time, set to 1 day (24 hours) in milliseconds.
         * 86400 seconds/day * 1000 milliseconds/second = 86,400,000 ms
         */
        final long SECONDS_IN_DAY = 86400L;
        final long MILLISECONDS_IN_SECOND = 1000L;

        final long tokenExpiration = MILLISECONDS_IN_SECOND * SECONDS_IN_DAY;

        return Jwts.builder()
                .subject(email)
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


    public String getEmailFromToken(String token) {
       return getClaims(token).getSubject();
    }
}
