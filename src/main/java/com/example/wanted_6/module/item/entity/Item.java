package com.example.wanted_6.module.item.entity;

import com.example.wanted_6.module.common.entity.Status;
import com.example.wanted_6.module.common.entity.BaseEntity;
import com.example.wanted_6.module.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import org.apache.coyote.BadRequestException;

import java.io.IOException;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    private Integer price;

    private Integer stock;

    @Enumerated(EnumType.STRING)
    private Status itemStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users seller;

    public void edit(String itemName, Integer price, Integer stock) {
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
        if (this.itemStatus.equals(Status.ITEM_SOLD_OUT) && this.stock > 0) {
            this.itemStatus = Status.ITEM_SELLING;
        } else if (!this.itemStatus.equals(Status.ITEM_SOLD_OUT) && this.stock <= 0) {
            this.itemStatus = Status.ITEM_SOLD_OUT;
        }
    }

    public void sell() {
        if(this.stock <= 0) {
            throw new IllegalArgumentException("재고 부족입니다.");
        }
        this.stock = this.stock - 1;
        if (this.stock == 0) {
            this.itemStatus = Status.ITEM_SOLD_OUT;
        }
    }
}
