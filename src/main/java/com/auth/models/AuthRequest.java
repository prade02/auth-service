package com.auth.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // needed by ObjectMapper to deserialize
public class AuthRequest {

    private String username;
    private String password;
}
