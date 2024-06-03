package com.example.wanted_6.module.item.controller;

import com.example.wanted_6.module.common.entity.Status;
import com.example.wanted_6.module.item.dto.payload.ItemEditPayload;
import com.example.wanted_6.module.item.dto.payload.ItemSavePayload;
import com.example.wanted_6.module.item.dto.payload.SearchCond;
import com.example.wanted_6.module.item.dto.result.ItemResult;
import com.example.wanted_6.module.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResult> saveItem(@RequestBody ItemSavePayload payload, @AuthenticationPrincipal Long userId) {
        ItemResult save = itemService.save(payload, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @GetMapping()
    public ResponseEntity<Page<ItemResult>> getItems(
            @RequestParam(name = "itemName", required = false) String itemName,
            @RequestParam(name = "status", required = false) Status status,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            @AuthenticationPrincipal Long userId) {
        Page<ItemResult> pageResult = itemService.findPage(new SearchCond(itemName, status), userId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate")));
        return ResponseEntity.status(HttpStatus.OK).body(pageResult);
    }


    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResult> getOneByItemId(@PathVariable(name = "itemId") Long itemId, @AuthenticationPrincipal Long userId) {
        log.info("왜 쳐 안됨");
        ItemResult result = itemService.findById(itemId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemResult> editItemByItemId(@PathVariable(name = "itemId") Long itemId, @RequestBody ItemEditPayload payload, @AuthenticationPrincipal Long userId) {
        ItemResult itemResult = itemService.editItemByItemId(payload, itemId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(itemResult);
    }
}
