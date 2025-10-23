package com.example.shoppinglist.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequestDto {
    private String product;
    private String amount;
}
