package com.wsd.commerce.model.dto.auth;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String refreshToken;
    private String expireTime;
}
