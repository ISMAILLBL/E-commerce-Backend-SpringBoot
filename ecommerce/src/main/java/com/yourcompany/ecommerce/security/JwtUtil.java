package com.yourcompany.ecommerce.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key;
    private final long expireMs;

    public JwtUtil(@Value("${ecommerce.jwt.secret}") String secret,
                   @Value("${ecommerce.jwt.expireMs}") long expireMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expireMs = expireMs;
    }

    public String generate(String subject) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expireMs);
        return Jwts.builder()
                   .setSubject(subject)
                   .setIssuedAt(now)
                   .setExpiration(expiry)
                   .signWith(key, SignatureAlgorithm.HS512)
                   .compact();
    }

    public String getSubject(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                   .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validate(String token) {
        try { getSubject(token); return true; }
        catch (JwtException | IllegalArgumentException ex) { return false; }
    }
}
