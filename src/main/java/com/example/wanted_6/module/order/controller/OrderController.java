package com.example.wanted_6.module.order.controller;

import com.example.wanted_6.module.common.entity.Status;
import com.example.wanted_6.module.item.dto.payload.SearchCond;
import com.example.wanted_6.module.order.dto.payload.OrderPayload;
import com.example.wanted_6.module.order.dto.result.OrderResult;
import com.example.wanted_6.module.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResult> addOrder(@RequestBody OrderPayload payload, @AuthenticationPrincipal Long userId) {
        OrderResult order = orderService.order(payload.getItemId(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping()
    public ResponseEntity<Page<OrderResult>> getOrders(
            @RequestParam(name = "itemName") String itemName,
            @RequestParam(name = "status") Status status,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            @AuthenticationPrincipal Long userId) {
        Page<OrderResult> pageResult = orderService.findAllByConsumerId(new SearchCond(itemName, status), userId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate")));
        return ResponseEntity.status(HttpStatus.OK).body(pageResult);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResult> getOneById(@PathVariable("orderId") Long orderId, @AuthenticationPrincipal Long userId) {
        OrderResult findOrder = orderService.findById(orderId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(findOrder);
    }

}
