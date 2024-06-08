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

    public void updateStatus(Status status, Long userId) {
        if (!this.seller.getId().equals(userId)) {
            throw new IllegalArgumentException("상품 판매자만 주문 상태를 변경 할 수 있습니다.");
        }
        if (status.equals(Status.ORDER_COMPLETE)) {
            this.item.sell();
        }
        this.orderStatus = status;
    }
}
