package com.auth.security.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
@AllArgsConstructor
public class JwtSecretKeyGenerator {

    private final JwtConfig jwtConfig;

    public Key getSecretKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
    }
}
