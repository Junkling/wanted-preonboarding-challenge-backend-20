package com.example.wanted_6.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> noSuchElementExceptionHandler(NoSuchElementException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    //커스텀 익셉션 사용 예
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResult> customExceptionHandler(CustomException e) {
        return ErrorResult.ofResponse(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> validException(MethodArgumentNotValidException e) {
        List<String> messages = getFieldErrorMessage(e);
        return ErrorResult.ofResponse(e, messages, HttpStatus.BAD_REQUEST);
    }

    private static List<String> getFieldErrorMessage(MethodArgumentNotValidException e) {
        List<String> messages = new ArrayList<>();
        List<FieldError> fieldErrors = e.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String error = fieldError.getField() + " : " + fieldError.getDefaultMessage();
            messages.add(error);
        }
        return messages;
    }
}
