package com.infy.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // In real apps, load from application.properties
    private static final String SECRET = "my-super-secret-key-which-should-be-long-enough-for-hmac-sha"; 
    private static final long EXPIRATION_MS = 86400000; // 1 day

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // ✅ Generate JWT with claims
    public String generateToken(Long alumniId, String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("alumniId", alumniId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key)  // New API (0.11.5)
                .compact();
    }

    // ✅ Extract claims safely
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Validate token
    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // ✅ Extract specific values
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }

    public Long extractAlumniId(String token) {
        return ((Number) extractClaims(token).get("alumniId")).longValue();
    }
}
