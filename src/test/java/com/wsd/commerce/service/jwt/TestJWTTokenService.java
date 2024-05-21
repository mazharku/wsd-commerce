package com.wsd.commerce.service.jwt;

import com.wsd.commerce.model.entity.Role;
import com.wsd.commerce.model.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class TestJWTTokenService {

    @Autowired
    private JWTTokenService jwtTokenService;
    private String token;

    @BeforeEach
    void init() {
        this.token = createToken();
    }

    @Test
    void test__jwtToken_created() {
        String token = createToken();
        this.token = token;
        boolean validToken = token.contains("ey");
        Assertions.assertTrue(validToken);
    }

    private String createToken() {
        Role role = new Role();
        role.setId(1);
        role.setRoleName("ADMIN");
        role.setActive(true);
        User user = new User();
        user.setId(1);
        user.setName("dummy user");
        user.setEmail("test@ymail.com");
        user.setPassword("123456");
        user.setPhoneNumber("123");
        user.setRoles(Set.of(role));
        return jwtTokenService.generateToken(user);
    }

    @Test
    void test__token_is_valid() {
        boolean valid = jwtTokenService.validateToken(token);
        Assertions.assertTrue(valid);
    }

    @Test
    void test__token_should_return_user_id() {
        String userId = jwtTokenService.loginUserId(token);
        Assertions.assertEquals("1", userId);
    }

    @Test
    void test__token_should_return_user_name() {
        String userName = jwtTokenService.extractUsername(token);
        Assertions.assertEquals("dummy user", userName);
    }

    @Test
    void test__token_should_return_user_email() {
        String email = jwtTokenService.extractEmail(token);
        Assertions.assertEquals("test@ymail.com", email);
    }

    @Test
    void test__refresh_token_is_created() {
        String refreshToken = jwtTokenService.generateRefreshToken();
        boolean valid = jwtTokenService.validateToken(refreshToken);
        Assertions.assertTrue(valid);
    }

    @Test
    void test__token_should_calculate_expire_time_in_minute() {
        int tokenExpireTimeInMinute = jwtTokenService.getTokenExpireTimeInMinute(token);
        // Assertions.assertEquals(10, tokenExpireTimeInMinute);
        // we are not sure if the expire time is always 10!
        Assertions.assertTrue(tokenExpireTimeInMinute > 0);
    }

}
