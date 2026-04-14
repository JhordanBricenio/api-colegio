package com.codej.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${app.jwt.secret:}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms:3600000}")
    private long jwtExpirationMs;

    // cached signing key
    private Key signingKey;

    private Key getSigningKey() {
        if (signingKey != null) return signingKey;

        try {
            if (jwtSecret == null || jwtSecret.isBlank()) {
                signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
                log.warn("JWT secret not provided. Generated ephemeral signing key. Tokens will be invalidated on restart. Set 'app.jwt.secret' env var to persist tokens.");
            } else {
                byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
                if (keyBytes.length < 32) {
                    // too short for HS256 (needs >= 256 bits = 32 bytes)
                    log.warn("Provided JWT secret is too short ({} bytes). Generating a secure key instead. Please use a secret with at least 32 bytes.", keyBytes.length);
                    signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
                } else {
                    signingKey = Keys.hmacShaKeyFor(keyBytes);
                }
            }
        } catch (Exception e) {
            log.error("Failed to create JWT signing key: {}", e.getMessage());
            // fallback to generated key
            signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        }
        return signingKey;
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateTokenForUser(String subject, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return generateToken(claims, subject);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException e) {
            log.warn("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

}
