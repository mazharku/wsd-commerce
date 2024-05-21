package com.wsd.commerce.service;

import com.wsd.commerce.model.dto.auth.LoginResponse;

public interface AuthenticationService {

    void signup();
    LoginResponse login();

    boolean logout();

}
