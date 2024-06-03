package com.example.wanted_6.module.user.dto.payload;

import lombok.Data;

@Data
public class SignUpPayload {
    private String username;
    private String nickname;
    private String password;
}
