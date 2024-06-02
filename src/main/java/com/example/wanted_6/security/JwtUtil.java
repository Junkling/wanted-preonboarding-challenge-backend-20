package com.example.wanted_6.security;

import com.example.wanted_6.security.payload.JwtTokenPayload;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@Slf4j
public class JwtUtil {
    @Value("${jwt.expiration_time.acess}")
    private long expirationTime;

    @Value("${jwt.secret}")
    private String secretCode;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secretCode.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(JwtTokenPayload jwtTokenPayload) {
        Date now = new Date();
        Date endTime = new Date(now.getTime() + expirationTime);
        return Jwts.builder()
                .claim("id", jwtTokenPayload.getUserId())
                .claim("username", jwtTokenPayload.getUsername())
                .claim("roles", jwtTokenPayload.getRoles())
                .claim("nickname", jwtTokenPayload.getNickname())
                .setIssuedAt(now)
                .setExpiration(endTime)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            log.info("[JwtValidation] : 토큰 만료");
            throw e;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("[JwtValidation] : 유효하지 않은 토큰");
            throw e;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public JwtTokenPayload parseToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            return JwtTokenPayload.builder()
                    .userId(claims.get("id", Long.class))
                    .username(claims.get("username", String.class))
                    .nickname(claims.get("nickname", String.class))
                    .roles((List<String>) claims.get("roles", List.class))
                    .build();
        }catch (Exception e) {
            log.info("토큰 파싱 오류");
            throw new BadCredentialsException("토큰이 올바르지 않습니다.");
        }
    }

    public String extractJwtFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        return null;
    }
}
