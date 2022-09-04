package com.auth.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.jwt")
@Getter
@Setter // needed to set property value post construction
public class JwtConfig {

    private String tokenPrefix;
    private String secret;
    private int expiresInDays;

    public String authorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
