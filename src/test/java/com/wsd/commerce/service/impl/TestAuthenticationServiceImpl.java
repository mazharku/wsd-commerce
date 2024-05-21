package com.wsd.commerce.service.impl;

import com.wsd.commerce.model.dto.auth.SignupRequest;
import com.wsd.commerce.model.entity.Role;
import com.wsd.commerce.model.entity.User;
import com.wsd.commerce.model.exceptions.ResourceNotFoundException;
import com.wsd.commerce.repository.RoleRepository;
import com.wsd.commerce.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AuthenticationServiceImpl.class)
public class TestAuthenticationServiceImpl {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationServiceImpl service;

    @Test
    void test__signup_create_new_user() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("dummy");
        signupRequest.setEmail("test@ymail.com");
        signupRequest.setPassword("123456");
        signupRequest.setRoles(List.of("USER"));

        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(new Role()));
        service.signup(signupRequest);

        verify(roleRepository, times(1)).findByRoleName("USER");
        verify(userRepository, times(1)).save(Mockito.any(User.class));
    }

    @Test
    void test__signup_will_fail_for_any_unknown_role() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("dummy");
        signupRequest.setEmail("test@ymail.com");
        signupRequest.setPassword("123456");
        signupRequest.setRoles(List.of("USER", "System"));

        when(roleRepository.findByRoleName("USER")).thenReturn(Optional.of(new Role()));

        ResourceNotFoundException response = assertThrows(ResourceNotFoundException.class, () -> service.signup(signupRequest));
        verify(roleRepository, times(1)).findByRoleName("USER");
        Assertions.assertEquals("Role System not found", response.getMessage());
    }

}
