package com.example.wanted_6.security;

import com.example.wanted_6.security.authentication.JwtAuthentication;
import com.example.wanted_6.security.provider.JwtProvider;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final JwtProvider jwtProvider;

    private AuthenticationManager authenticationManager;

    @PostConstruct
    public void init() {
        this.authenticationManager = new ProviderManager(jwtProvider);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtUtil.extractJwtFromRequest(request);
            if (StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
                JwtAuthentication jwtAuthentication = new JwtAuthentication(token);
                authenticationManager.authenticate(jwtAuthentication);
                if (!jwtAuthentication.isAuthenticated()) {
                    throw new BadCredentialsException("인증이 되지 않았습니다.");
                }
                SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
            }
        } catch (JwtException | IllegalArgumentException e) {
            SecurityContextHolder.clearContext();
            response.sendError(401, e.getMessage());
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.sendError(400, e.getMessage());
        }
    }
}
