package com.example.wanted_6.module.user.controller;

import com.example.wanted_6.module.item.dto.result.ItemResult;
import com.example.wanted_6.module.item.service.ItemService;
import com.example.wanted_6.module.order.dto.result.OrderResult;
import com.example.wanted_6.module.order.service.OrderService;
import com.example.wanted_6.module.user.dto.payload.OrderUpdatePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {
    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping("/items")
    public ResponseEntity<Page<ItemResult>> getItemsBySeller(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            @AuthenticationPrincipal Long userId) {
        Page<ItemResult> pageResult = itemService.findPageBySellerId(userId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate")));
        return ResponseEntity.status(HttpStatus.OK).body(pageResult);
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<OrderResult>> getOrdersBySeller(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            @AuthenticationPrincipal Long sellerId) {
        Page<OrderResult> result = orderService.findAllBySellerId(sellerId, PageRequest.of(page, size));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/orders/{orderId}")
    public ResponseEntity<OrderResult> updateOrderStatus(
            @PathVariable(name = "orderId") Long orderId,
            @AuthenticationPrincipal Long userId,
            @RequestBody OrderUpdatePayload payload) {
        OrderResult result = orderService.updateStatusByOrderId(orderId, userId, payload);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
