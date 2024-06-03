package com.example.wanted_6.module.item.dto.result;

import com.example.wanted_6.module.item.entity.Item;
import com.example.wanted_6.module.common.entity.Status;
import com.example.wanted_6.module.user.dto.result.UserSimpleResult;
import lombok.Data;

@Data
public class ItemResult {
    private Long id;

    private String itemName;

    private Integer price;

    private Integer stock;

    private Status itemStatus;

    private UserSimpleResult sellerInfo;

    public static ItemResult of(Item item) {
        ItemResult itemResult = new ItemResult();
        itemResult.setId(item.getId());
        itemResult.setItemName(item.getItemName());
        itemResult.setStock(item.getStock());
        itemResult.setPrice(item.getPrice());
        itemResult.setItemStatus(item.getItemStatus());
        itemResult.setSellerInfo(UserSimpleResult.of(item.getSeller()));
        return itemResult;
    }
}
