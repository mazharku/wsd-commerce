package com.wsd.commerce.service.jwt;

import com.wsd.commerce.model.LoginUserDetails;
import com.wsd.commerce.model.entity.User;
import com.wsd.commerce.model.exceptions.ResourceNotFoundException;
import com.wsd.commerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("user not found"));
            return new LoginUserDetails(user);

    }
}
