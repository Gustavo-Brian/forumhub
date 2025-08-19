package com.alura.forumhub.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final Key key;
    private final long expirationMs;

    public JwtTokenProvider(@Value("${security.jwt.secret}") String secret,
                            @Value("${security.jwt.expiration}") long expirationMs){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    public String generateToken(String subject){
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder().setSubject(subject).setIssuedAt(now).setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public String getSubject(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }
}
