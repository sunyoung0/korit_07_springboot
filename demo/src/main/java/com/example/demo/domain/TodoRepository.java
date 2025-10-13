package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByUserId(Long id);

    void deleteByUserAndIsCompleted(User user, boolean isCompleted);
}
