package com.example.wanted_6.module.user.controller;

import com.example.wanted_6.module.user.dto.payload.SignInPayLoad;
import com.example.wanted_6.module.user.dto.payload.SignUpPayload;
import com.example.wanted_6.module.user.dto.result.JwtTokenResult;
import com.example.wanted_6.module.user.dto.result.UserSimpleResult;
import com.example.wanted_6.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserSimpleResult> signUp(@RequestBody SignUpPayload payload) {
        UserSimpleResult userSimpleResult = userService.signUp(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSimpleResult);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResult> login(@RequestBody SignInPayLoad payLoad) {
        JwtTokenResult jwtTokenResult = userService.signIn(payLoad);
        return ResponseEntity.status(HttpStatus.OK).body(jwtTokenResult);
    }
}
