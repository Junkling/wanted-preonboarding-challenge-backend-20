package com.example.wanted_6.module.order.repository;

import com.example.wanted_6.module.common.entity.Status;
import com.example.wanted_6.module.order.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Page<Orders> findAllByConsumerId(Long userId, Pageable pageable);

    Optional<Orders> findByConsumerIdAndItemId(Long userId, Long id);

    Page<Orders> findAllByItemItemNameContainingAndOrderStatusEquals(String itemName, Status status, Pageable pageable);

    Page<Orders> findAllByItemItemNameContaining(String itemName, Pageable pageable);

    Page<Orders> findAllByOrderStatusEquals(Status status, Pageable pageable);

    Page<Orders> findAllBySellerId(Long userId, Pageable pageable);

}