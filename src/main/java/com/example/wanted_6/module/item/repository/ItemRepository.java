package com.example.wanted_6.module.item.repository;

import com.example.wanted_6.module.item.entity.Item;
import com.example.wanted_6.module.common.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAllByItemNameContainingAndItemStatusEquals(String itemName, Status itemStatus, Pageable pageable);
    Page<Item> findAllByItemStatusEquals(Status itemStatus, Pageable pageable);
    Page<Item> findAllByItemNameContaining(String itemName, Pageable pageable);

    Page<Item> findAllBySellerId(Long sellerId, Pageable pageable);
}
