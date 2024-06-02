package com.example.wanted_6.module.item.dto.payload;

import com.example.wanted_6.module.common.entity.Status;
import lombok.Data;

@Data
public class ItemSavePayload {
    private String itemName;

    private Integer price;

    private Integer stock;

    private Status itemStatus;

}
