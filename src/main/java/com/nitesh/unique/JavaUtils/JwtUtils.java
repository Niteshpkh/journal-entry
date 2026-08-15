package com.nitesh.unique.JavaUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtils {

    private static final String SECRET_KEY =
            "my-super-secret-key-for-jwt-authentication-123456789";

    private static final long EXPIRATION_TIME =
            1000 * 60 * 60; // 1 hour


    // Create signing key
    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }


    // Generate JWT token
    public String generateToken(String userDetails) {

        return Jwts.builder()
                .subject(userDetails)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + EXPIRATION_TIME
                        )
                )
                .signWith(getSigningKey())
                .compact();
    }


    // Extract username from token
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }


    // Extract expiration date
    public Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }


    // Extract any claim
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
    ) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }


    // Extract all claims
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    // Check if token is expired
    public boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }


    // Validate token
    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
}