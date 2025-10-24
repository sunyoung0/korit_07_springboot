package com.example.shoppinglist;

import com.example.shoppinglist.domain.Item;
import com.example.shoppinglist.domain.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShoppinglistApplication implements CommandLineRunner {

	private final ItemRepository itemRepository;

    public ShoppinglistApplication(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(ShoppinglistApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Item item = new Item("책", "2");
		itemRepository.save(new Item("책", "20"));
		itemRepository.save(new Item("자동차", "1"));
		itemRepository.save(new Item("커피", "5"));
	}
}