package com.example.wanted_6.module.order.entity;

import com.example.wanted_6.module.common.entity.BaseEntity;
import com.example.wanted_6.module.common.entity.Status;
import com.example.wanted_6.module.item.entity.Item;
import com.example.wanted_6.module.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer orderPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users consumer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users seller;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @Enumerated(EnumType.STRING)
    private Status orderStatus;

    public void updateStatus(Status status) {
        if (status.equals(Status.ORDER_COMPLETE)) {
            this.item.sell();
        }
        this.orderStatus = status;
    }
}
