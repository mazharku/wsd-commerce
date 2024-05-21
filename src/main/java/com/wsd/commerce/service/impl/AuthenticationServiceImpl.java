package com.wsd.commerce.service.impl;

import com.wsd.commerce.model.dto.auth.LogInRequest;
import com.wsd.commerce.model.dto.auth.LoginResponse;
import com.wsd.commerce.model.dto.auth.SignupRequest;
import com.wsd.commerce.model.entity.Role;
import com.wsd.commerce.model.entity.User;
import com.wsd.commerce.model.exceptions.ResourceNotFoundException;
import com.wsd.commerce.repository.RoleRepository;
import com.wsd.commerce.repository.UserRepository;
import com.wsd.commerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                .map(role-> roleRepository.findByRoleName(role)
                        .orElseThrow(()-> new ResourceNotFoundException("Role %s not found".formatted(role))))
                .collect(Collectors.toSet());
    }

    @Override
    public LoginResponse login(LogInRequest logInRequest) {
        return null;
    }

    @Override
    public boolean logout(String token) {
        return false;
    }
}
