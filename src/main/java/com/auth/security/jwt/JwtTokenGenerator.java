package com.auth.security.jwt;

import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JwtTokenGenerator {

  private final JwtSecretKeyGenerator jwtSecretKeyGenerator;
  private final JwtConfig jwtConfig;

  public String generateToken(Authentication authenticationResult) {
    if (!authenticationResult.isAuthenticated())
      throw new AccessDeniedException("User is not authenticated but tried to generate JWT token");
    var authorities =
        authenticationResult.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    return Jwts.builder()
            .setSubject(authenticationResult.getName())
            .claim("authorities", authorities)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(
                Date.from(
                    LocalDateTime.now()
                        .plusDays(jwtConfig.getExpiresInDays())
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
            .signWith(jwtSecretKeyGenerator.getSecretKey())
            .compact();
  }
}
