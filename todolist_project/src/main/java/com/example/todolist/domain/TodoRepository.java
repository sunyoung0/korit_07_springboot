package com.example.todolist.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    void deleteByAppuserAndIsCompleted(AppUser currentUser, boolean b);
}
