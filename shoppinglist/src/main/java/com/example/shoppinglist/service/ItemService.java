package com.example.shoppinglist.service;

import com.example.shoppinglist.domain.Item;
import com.example.shoppinglist.domain.ItemRepository;
import com.example.shoppinglist.dto.ItemRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // 전체 쇼핑 리스트 불러오기
    @Transactional
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    // ITEM 등록하기
    @Transactional
    public Item createItem(ItemRequestDto itemRequestDto) {
        Item newItem = new Item(itemRequestDto.getProduct(), itemRequestDto.getAmount());

        return itemRepository.save(newItem);
    }

    // 쇼핑리스트 제품 등록하기




}
