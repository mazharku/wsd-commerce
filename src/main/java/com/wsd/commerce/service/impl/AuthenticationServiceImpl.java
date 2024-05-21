package com.wsd.commerce.service.impl;

import com.wsd.commerce.model.dto.auth.LogInRequest;
import com.wsd.commerce.model.dto.auth.LoginResponse;
import com.wsd.commerce.model.dto.auth.SignupRequest;
import com.wsd.commerce.model.entity.AuthToken;
import com.wsd.commerce.model.entity.Role;
import com.wsd.commerce.model.entity.User;
import com.wsd.commerce.model.exceptions.ResourceNotFoundException;
import com.wsd.commerce.repository.AuthTokenRepository;
import com.wsd.commerce.repository.RoleRepository;
import com.wsd.commerce.repository.UserRepository;
import com.wsd.commerce.service.AuthenticationService;
import com.wsd.commerce.service.jwt.JWTTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JWTTokenService tokenService;
    private final AuthTokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public void signup(SignupRequest signupRequest) {
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setIsActive(true);
        Set<Role> roles = createUserRole(signupRequest.getRoles());
        user.setRoles(roles);
        userRepository.save(user);
    }

    private Set<Role> createUserRole(List<String> roles) {
        return roles.stream()
                .map(role -> roleRepository.findByRoleName(role)
                        .orElseThrow(() -> new ResourceNotFoundException("Role %s not found".formatted(role))))
                .collect(Collectors.toSet());
    }

    @Override
    public LoginResponse login(LogInRequest logInRequest) {
        User user = userRepository.findByEmail(logInRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("No registered user is found"));

        UsernamePasswordAuthenticationToken userAuthenticate = new UsernamePasswordAuthenticationToken(logInRequest.getEmail(), logInRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(userAuthenticate);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenService.generateToken(user);
        int tokenExpireTimeInMinute = tokenService.getTokenExpireTimeInMinute(token);
        String refreshToken = tokenService.generateRefreshToken();

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setExpireTime(String.valueOf(tokenExpireTimeInMinute));
        response.setRefreshToken(refreshToken);
        createAuthToken(user, token);
        return response;
    }

    private void createAuthToken(User user, String token) {
        AuthToken authToken = new AuthToken();
        authToken.setUser(user);
        authToken.setTokenCreationTime(LocalDateTime.now());
        authToken.setToken(token);
        tokenRepository.save(authToken);
    }

    @Override
    public void logout(String token) {
        tokenRepository.findByToken(token)
                .ifPresentOrElse(tokenRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("token is not exist!");
                        });
    }
}
