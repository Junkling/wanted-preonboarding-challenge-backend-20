package com.example.wanted_6.module.common.entity;

import lombok.*;

@AllArgsConstructor
@Getter
public enum Status {
    ITEM_SELLING("판매중"),
    ITEM_SOLD_OUT("판매완료"),

    ORDER_RESERVATION("예약 중"),
    ORDER_COMPLETE("구매 완료"),
    ORDER_CONFIRM("구매 확정"),
    ;
    private final String value;
}
