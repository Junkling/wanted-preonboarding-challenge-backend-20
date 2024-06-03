package com.example.wanted_6.module.user.controller;

import com.example.wanted_6.module.order.service.OrderService;
import com.example.wanted_6.module.user.dto.payload.SignInPayLoad;
import com.example.wanted_6.module.user.dto.payload.SignUpPayload;
import com.example.wanted_6.module.user.dto.result.JwtTokenResult;
import com.example.wanted_6.module.user.dto.result.UserSimpleResult;
import com.example.wanted_6.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserSimpleResult> signUp(@RequestBody SignUpPayload payload) {
        log.info("sign up 호출 username={} , password ={}", payload.getUsername(), payload.getPassword());
        UserSimpleResult userSimpleResult = userService.signUp(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSimpleResult);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResult> login(@RequestBody SignInPayLoad payLoad) {
        JwtTokenResult jwtTokenResult = userService.signIn(payLoad);
        return ResponseEntity.status(HttpStatus.OK).body(jwtTokenResult);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.status(HttpStatus.OK).body("test");
    }
}
