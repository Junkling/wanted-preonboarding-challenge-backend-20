package com.example.wanted_6.module.user.service;

import com.example.wanted_6.global.exception.CustomException;
import com.example.wanted_6.global.exception.ErrorCode;
import com.example.wanted_6.module.user.dto.payload.SignInPayLoad;
import com.example.wanted_6.module.user.dto.payload.SignUpPayload;
import com.example.wanted_6.module.user.dto.result.JwtTokenResult;
import com.example.wanted_6.module.user.dto.result.UserSimpleResult;
import com.example.wanted_6.module.user.entity.Users;
import com.example.wanted_6.module.user.repository.UserRepository;
import com.example.wanted_6.security.JwtUtil;
import com.example.wanted_6.security.payload.JwtTokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.example.wanted_6.global.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserSimpleResult signUp(SignUpPayload signupPayload) {
        Users saved = userRepository.save(
                Users.builder()
                        .username(signupPayload.getUsername())
                        .nickname(signupPayload.getNickname())
                        .role("USER")
                        .password(passwordEncoder.encode(signupPayload.getPassword()))
                        .build());
        return UserSimpleResult.of(saved);
    }

    public JwtTokenResult signIn(SignInPayLoad signInPayLoad) {
        Users findUser = userRepository.findByUsername(signInPayLoad.getUsername()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        if (!passwordEncoder.matches(signInPayLoad.getPassword(), findUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        String accessToken = jwtUtil.generateToken(
                JwtTokenPayload.builder()
                        .userId(findUser.getId())
                        .nickname(findUser.getNickname())
                        .username(findUser.getUsername())
                        .roles(List.of(findUser.getRole()))
                        .roles(List.of(findUser.getRole()))
                        .build());
        return new JwtTokenResult(accessToken);
    }
}
