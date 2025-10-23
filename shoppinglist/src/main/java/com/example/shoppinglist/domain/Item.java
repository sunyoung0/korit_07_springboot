package com.example.shoppinglist.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private String amount;

    public Item(String product, String amount) {
        this.product = product;
        this.amount = amount;
    }
}
