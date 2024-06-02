package com.example.wanted_6.module.item.dto.payload;

import lombok.Data;

@Data
public class ItemEditPayload {
    private String name;
    private Integer price;
    private Integer stock;
}
