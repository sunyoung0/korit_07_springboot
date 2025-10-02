package com.example.todolist;

import com.example.todolist.domain.AppUser;
import com.example.todolist.domain.AppUserRepository;
import com.example.todolist.domain.Todo;
import com.example.todolist.domain.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodolistApplication implements CommandLineRunner {

	private final TodoRepository todoRepository;
	private final AppUserRepository appUserRepository;

    public TodolistApplication(TodoRepository todoRepository, AppUserRepository appUserRepository) {
        this.todoRepository = todoRepository;
        this.appUserRepository = appUserRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// user 객체 추가
		AppUser appUser = new AppUser("user", "$2a$12$oTwm0omEKaCleY9jD9MFaenCujqmyozQEBXqi4hFKQSJAvcEkjbiO", "USER");
		appUserRepository.save(appUser);

		todoRepository.save(new Todo("오늘 할일 많음", appUser));

	}




}
