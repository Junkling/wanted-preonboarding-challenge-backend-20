package com.example.wanted_6.module.order.service;

import com.example.wanted_6.module.common.entity.Status;
import com.example.wanted_6.module.item.dto.payload.SearchCond;
import com.example.wanted_6.module.item.dto.result.ItemResult;
import com.example.wanted_6.module.item.entity.Item;
import com.example.wanted_6.module.item.repository.ItemRepository;
import com.example.wanted_6.module.order.dto.result.OrderResult;
import com.example.wanted_6.module.order.entity.Orders;
import com.example.wanted_6.module.order.repository.OrderRepository;
import com.example.wanted_6.module.user.dto.payload.OrderUpdatePayload;
import com.example.wanted_6.module.user.dto.result.UserSimpleResult;
import com.example.wanted_6.module.user.entity.Users;
import com.example.wanted_6.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderResult order(Long userId, Long itemId) {
        Users users = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("유저 정보를 찾을 수 없습니다."));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NoSuchElementException("해당하는 상품이 없습니다."));

        Orders saved = orderRepository.save(
                Orders.builder()
                        .consumer(users)
                        .orderPrice(item.getPrice())
                        .item(item)
                        .seller(item.getSeller())
                        .orderStatus(Status.ORDER_RESERVATION)
                        .build());

        return toOrderResult(saved);
    }

    public Page<OrderResult> findAllByConsumerId(SearchCond cond, Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("회원정보를 찾을 수 없습니다.");
        }
        if (StringUtils.hasText(cond.getItemName())) {
            if (cond.getStatus() != null && StringUtils.hasText(cond.getStatus().getValue())) {
                return orderRepository.findAllByItemItemNameContainingAndOrderStatusEquals(cond.getItemName(), cond.getStatus(), pageable).map(this::toOrderResult);
            } else {
                return orderRepository.findAllByItemItemNameContaining(cond.getItemName(), pageable).map(this::toOrderResult);
            }
        } else {
            if (cond.getStatus() != null && StringUtils.hasText(cond.getStatus().getValue())) {
                return orderRepository.findAllByOrderStatusEquals(cond.getStatus(), pageable).map(this::toOrderResult);
            }
        }
        return orderRepository.findAllByConsumerId(userId, pageable).map(this::toOrderResult);
    }

    public OrderResult findById(Long orderId, Long userId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new NoSuchElementException("주문 정보를 찾을 수 없습니다."));
        if (!orders.getConsumer().getId().equals(userId)) {
            throw new IllegalArgumentException("주문자가 아닙니다.");
        }
        return toOrderResult(orders);
    }

    public Page<OrderResult> findAllBySellerId(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("유저 정보를 찾을 수 없습니다.");
        }
        return orderRepository.findAllBySellerId(userId, pageable).map(this::toOrderResult);
    }

    @Transactional
    public OrderResult updateStatusByOrderId(Long orderId, Long userId, OrderUpdatePayload payload) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new NoSuchElementException("주문 정보가 없습니다."));
        if (!orders.getSeller().getId().equals(userId)) {
            throw new IllegalArgumentException("상품 판매자만 주문 상태를 변경 할 수 있습니다.");
        }
        orders.updateStatus(payload.getStatus());
        return toOrderResult(orders);
    }

    private OrderResult toOrderResult(Orders orders) {
        OrderResult result = new OrderResult();
        result.setId(orders.getId());
        result.setOrderPrice(orders.getOrderPrice());
        result.setOrderStatus(orders.getOrderStatus());
        result.setItem(ItemResult.of(orders.getItem()));
        result.setConsumer(UserSimpleResult.of(orders.getConsumer()));
        return result;
    }

}
