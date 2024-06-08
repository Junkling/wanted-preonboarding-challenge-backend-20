package com.example.wanted_6.module.item.service;

import com.example.wanted_6.global.exception.CustomException;
import com.example.wanted_6.module.common.entity.Status;
import com.example.wanted_6.module.item.dto.payload.ItemEditPayload;
import com.example.wanted_6.module.item.dto.payload.ItemSavePayload;
import com.example.wanted_6.module.item.dto.payload.SearchCond;
import com.example.wanted_6.module.item.dto.result.ItemResult;
import com.example.wanted_6.module.item.entity.Item;
import com.example.wanted_6.module.item.repository.ItemRepository;
import com.example.wanted_6.module.order.entity.Orders;
import com.example.wanted_6.module.order.repository.OrderRepository;
import com.example.wanted_6.module.user.dto.result.UserSimpleResult;
import com.example.wanted_6.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static com.example.wanted_6.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public ItemResult save(ItemSavePayload payload, Long userId) {
        Item save = itemRepository.save(
                Item.builder()
                        .itemName(payload.getItemName())
                        .itemStatus(payload.getItemStatus())
                        .price(payload.getPrice())
                        .stock(payload.getStock())
                        .seller(userRepository.findById(userId).orElseThrow(() -> new CustomException(ITEM_NOT_FOUND)))
                        .build());
        return toItemResult(save, userId);
    }

    @Transactional
    public ItemResult editItemByItemId(ItemEditPayload payload, Long itemId, Long userId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new CustomException(ITEM_NOT_FOUND));
        if (!item.getSeller().getId().equals(userId)) {
            throw new CustomException(SELLER_FORBIDDEN);
        }
        item.edit(payload.getName(), payload.getPrice(), payload.getStock());
        return toItemResult(item, userId);
    }

    public ItemResult findById(Long id, Long userId) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new CustomException(ITEM_NOT_FOUND));
        return toItemResult(item, userId);
    }

    public Page<ItemResult> findPage(SearchCond cond, Long userId, Pageable pageable) {
        if (StringUtils.hasText(cond.getItemName())) {
            if (cond.getStatus() != null && StringUtils.hasText(cond.getStatus().getValue())) {
                return itemRepository.findAllByItemNameContainingAndItemStatusEquals(cond.getItemName(), cond.getStatus(), pageable).map((i) -> toItemResult(i, userId));
            } else {
                return itemRepository.findAllByItemNameContaining(cond.getItemName(), pageable).map((i) -> toItemResult(i, userId));
            }
        } else {
            if (cond.getStatus() != null && StringUtils.hasText(cond.getStatus().getValue())) {
                return itemRepository.findAllByItemStatusEquals(cond.getStatus(), pageable).map((i) -> toItemResult(i, userId));
            }
        }
        return itemRepository.findAll(pageable).map((i) -> toItemResult(i, userId));
    }

    public Page<ItemResult> findPageBySellerId(Long userId, Pageable pageRequest) {
        if (!userRepository.existsById(userId)) {
            throw new CustomException(USER_NOT_FOUND);
        }
        return itemRepository.findAllBySellerId(userId, pageRequest).map((i) -> toItemResult(i, userId));
    }

    private ItemResult toItemResult(Item item, Long userId) {
        Status status;
        if (userId == null || !userRepository.existsById(userId)) {
            status = item.getItemStatus();
        } else {
            Orders findOrder = orderRepository.findByConsumerIdAndItemId(userId, item.getId()).orElse(null);
            status = findOrder == null ? item.getItemStatus() : findOrder.getOrderStatus();
        }
        ItemResult itemResult = new ItemResult();
        itemResult.setId(item.getId());
        itemResult.setPrice(item.getPrice());
        itemResult.setStock(item.getStock());
        itemResult.setItemName(item.getItemName());
        itemResult.setSellerInfo(UserSimpleResult.of(item.getSeller()));
        itemResult.setItemStatus(status);
        return itemResult;
    }

}
