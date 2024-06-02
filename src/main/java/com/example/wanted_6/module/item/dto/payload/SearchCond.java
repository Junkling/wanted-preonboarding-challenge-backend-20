package com.example.wanted_6.module.item.dto.payload;

import com.example.wanted_6.module.common.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCond {
    private String itemName;
    private Status status;
}
