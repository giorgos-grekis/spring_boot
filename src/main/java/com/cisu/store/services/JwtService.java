package com.cisu.store.services;

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

    public String generateToken(String email) {
//        /**
//         * 86400 -> 1 days in seconds
//         * 1000 -> need to transform into milliseconds
//         */
//        final long tokenExpiration = 1000 * 86400;

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
     * * @param token The JWT string to validate.
     * @return {@code true} if the token is valid and not expired; {@code false} otherwise,
     * including if it is malformed, invalidly signed, or expired.
     */
    public boolean validateToken(String token) {
        try {
            var claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.getExpiration().after(new Date());
        }
        catch (JwtException e) {
            return false;
        }
    }

}
