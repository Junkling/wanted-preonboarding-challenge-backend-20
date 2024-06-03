package com.example.wanted_6.security.provider;

import com.example.wanted_6.security.JwtUtil;
import com.example.wanted_6.security.authentication.JwtAuthentication;
import com.example.wanted_6.security.payload.JwtTokenPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider implements AuthenticationProvider {
    private final JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            throw new BadCredentialsException("지원하지 않는 인증 방식입니다.");
        }
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        JwtTokenPayload jwtTokenPayload = jwtUtil.parseToken(jwtAuthentication.getToken());
        authentication.setAuthenticated(true);
        ((JwtAuthentication) authentication).setJwtTokenPayload(jwtTokenPayload);

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
