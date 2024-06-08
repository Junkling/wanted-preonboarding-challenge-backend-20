package com.example.wanted_6.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    ErrorCode errorCode;

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
}