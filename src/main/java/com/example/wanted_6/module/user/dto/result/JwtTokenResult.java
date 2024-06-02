package com.example.wanted_6.module.user.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokenResult {
    private String accessToken;
}
