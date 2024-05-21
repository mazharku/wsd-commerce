package com.wsd.commerce.controller;

import com.wsd.commerce.annotation.SecureAPI;
import com.wsd.commerce.model.dto.auth.LogInRequest;
import com.wsd.commerce.model.dto.auth.LoginResponse;
import com.wsd.commerce.model.dto.auth.SignupRequest;
import com.wsd.commerce.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller")
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
        service.signup(signupRequest);
        return ResponseEntity.ok("User is created successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LogInRequest logInRequest) {
        LoginResponse loginResponse = service.login(logInRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    @SecureAPI
    @Operation(description = "user must authenticate before logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            service.logout(token);
            logoutHandler.logout(request, null, authentication);
        }
        return ResponseEntity.ok("logout successfully!");
    }

}
