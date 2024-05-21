package com.wsd.commerce.service;

import com.wsd.commerce.model.dto.auth.LogInRequest;
import com.wsd.commerce.model.dto.auth.LoginResponse;
import com.wsd.commerce.model.dto.auth.SignupRequest;

public interface AuthenticationService {

    void signup(SignupRequest signupRequest);
    LoginResponse login(LogInRequest logInRequest);

    boolean logout(String token);

}
