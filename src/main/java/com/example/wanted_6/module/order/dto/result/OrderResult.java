package com.example.wanted_6.module.order.dto.result;

import com.example.wanted_6.module.common.entity.Status;
import com.example.wanted_6.module.item.dto.result.ItemResult;
import com.example.wanted_6.module.user.dto.result.UserSimpleResult;
import lombok.Data;


@Data
public class OrderResult {
    private Long id;

    private Integer orderPrice;

    private UserSimpleResult consumer;

    private ItemResult item;

    private Status orderStatus;
}
