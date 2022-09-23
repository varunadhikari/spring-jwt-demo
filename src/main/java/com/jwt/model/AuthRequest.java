package com.jwt.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    String username;
    String password;
    String role;
}
