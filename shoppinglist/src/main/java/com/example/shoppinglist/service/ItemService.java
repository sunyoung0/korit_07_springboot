package com.example.shoppinglist.service;

import com.example.shoppinglist.domain.Item;
import com.example.shoppinglist.domain.ItemRepository;
import com.example.shoppinglist.dto.ItemRequestDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
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

    // ITEM 수정하기
    @Transactional
    public Item updateItem(Long id, ItemRequestDto updateDto) throws AccessDeniedException {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 todo가 없습니다. id : " + id));

        item.setProduct(updateDto.getProduct());
        item.setAmount(updateDto.getAmount());

        return itemRepository.save(item);
    }

    // ITEM 삭제하기
    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 todo가 없습니다. id : " + id));

        itemRepository.delete(item);
    }






}
