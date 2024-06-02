package com.example.wanted_6.security.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class JwtTokenPayload {
    private Long userId;
    private String username;
    private String nickname;
    private List<String> roles = new ArrayList<>();
}
