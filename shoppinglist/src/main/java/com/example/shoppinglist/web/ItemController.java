package com.example.shoppinglist.web;

import com.example.shoppinglist.domain.Item;
import com.example.shoppinglist.dto.ItemRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.shoppinglist.service.ItemService;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // 전체 항목 조회
    @GetMapping
    public ResponseEntity<List<Item>> getItems() {
        return ResponseEntity.ok(itemService.getItems());
    }

    // item 등록
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody ItemRequestDto itemRequestDto) {
        Item createItem = itemService.createItem(itemRequestDto);
        return new ResponseEntity<>(createItem, HttpStatus.CREATED);
    }

    // item 수정
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(Long id, @RequestBody ItemRequestDto updateDto) throws AccessDeniedException {
        return ResponseEntity.ok(itemService.updateItem(id, updateDto));
    }

    // item 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(Long id) throws AccessDeniedException {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }


}
