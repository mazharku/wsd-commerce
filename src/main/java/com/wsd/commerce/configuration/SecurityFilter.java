package com.wsd.commerce.configuration;

import com.wsd.commerce.repository.AuthTokenRepository;
import com.wsd.commerce.service.jwt.JWTTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final JWTTokenService jwtTokenService;
    private final AuthTokenRepository tokenRepository;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            validateToken(token);
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                List<String> authorities = jwtTokenService.extractAuthorities(token);
                List<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream().map(SimpleGrantedAuthority::new).toList();

                if (jwtTokenService.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(null, null, simpleGrantedAuthorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateToken(String token) {
        tokenRepository.findByToken(token)
                .orElseThrow(() -> new SecurityException("Token is not found!"));
        jwtTokenService.validateToken(token);
    }


}
