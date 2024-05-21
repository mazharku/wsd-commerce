package com.wsd.commerce.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class SignupRequest {
    @NotBlank
    String name;
    @Email
    String email;
    @NotBlank @Size(min = 5, message = "password length must be gather than 5 chars")
    String password;
    List<String> roles;
}
