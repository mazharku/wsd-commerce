package com.wsd.commerce.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LogInRequest {
    @Email
    private String email;
    @NotEmpty @Size(min = 5, message = "password length must be gather than 5 chars")
    private String password;
}
