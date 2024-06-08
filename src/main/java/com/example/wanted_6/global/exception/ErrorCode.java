package com.example.wanted_6.global.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.example.wanted_6.global.exception.ErrorDefinition.*;
import static com.example.wanted_6.global.exception.ErrorDefinition.NOT_FOUND;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // DB 조회 실패 에러 코드
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, NOT_FOUND, "유저 정보를 찾을 수 없습니다."),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, NOT_FOUND, "상품 정보를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, NOT_FOUND, "주문 정보를 찾을 수 없습니다."),

    SELLER_FORBIDDEN(FORBIDDEN, USER_FORBIDDEN, "해당 상품의 판매자만 접근 가능합니다."),
    CONSUMER_FORBIDDEN(FORBIDDEN, USER_FORBIDDEN, "해당 상품의 구매자만 접근 가능합니다."),

    //JWT 실패 코드
    JWT_TIME_EXP(UNAUTHORIZED, SECURE, "토큰 시간이 만료되었습니다."),
    JWT_INVALID(FORBIDDEN, SECURE, "유효하지 않은 토큰이 입력되었습니다."),
    AUTHENTICATION_BLOCKED(FORBIDDEN, SECURE, "로그인 후 사용 가능한 서비스 입니다."),
    JWT_TYPE_ERROR(FORBIDDEN, SECURE, "토큰 타입이 잘못되었습니다."),
    ;


    private final HttpStatus httpStatus;
    private final ErrorDefinition errorDefinition;
    private final String message;
}