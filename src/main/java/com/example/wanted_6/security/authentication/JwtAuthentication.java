package com.example.wanted_6.security.authentication;

import com.example.wanted_6.security.payload.JwtTokenPayload;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class JwtAuthentication implements Authentication {
    private final String token;
    @Setter
    private JwtTokenPayload jwtTokenPayload;
    private boolean authenticated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return jwtTokenPayload.getRoles().stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return jwtTokenPayload;
    }

    @Override
    public Object getPrincipal() {
        return jwtTokenPayload.getUserId();
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return jwtTokenPayload.getUsername();
    }

}
