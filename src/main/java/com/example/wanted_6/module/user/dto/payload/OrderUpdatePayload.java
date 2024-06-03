package com.example.wanted_6.module.user.dto.payload;

import com.example.wanted_6.module.common.entity.Status;
import lombok.Data;

@Data
public class OrderUpdatePayload {
    private Status status;
}
