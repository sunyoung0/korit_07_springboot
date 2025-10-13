package com.example.todolist.web;

import com.example.todolist.domain.Todo;
import com.example.todolist.dto.TodoRequestRecord;
import com.example.todolist.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // 전체 조회
    @GetMapping("/todos")
    public List<Todo> getTodos() {
        return todoService.getTodos();
    }

    // id 별 조회
    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable Long id) {
        return todoService.getTodoById(id)
                .map(owner -> ResponseEntity.ok().body(owner))
                .orElse(ResponseEntity.notFound().build());
    }

    // 작성
    @PostMapping("/todos")
    public ResponseEntity<Todo> addTodo(@RequestBody TodoRequestRecord todoRecord) {
        Todo savedTodo = todoService.addTodo(todoRecord);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    //  내용 수정
    @PutMapping("/todos/putContent/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        return todoService.updateTodo(id, todoDetails)
                .map(updateTodo -> ResponseEntity.ok().body(updateTodo))
                .orElse(ResponseEntity.notFound().build());
    }

    // 하나 삭제
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        if(todoService.deleteTodo(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //  특정 id의 할 일 완료 상태를 변경
    @PutMapping("/todos/{id}")
    public ResponseEntity<Todo> updateTodoStatus(@PathVariable Long id) {
        return todoService.updateTodoStatus(id)
                .map(updateStatusTodo -> ResponseEntity.ok().body(updateStatusTodo))
                .orElse(ResponseEntity.notFound().build());
    }

    // 현재 로그인된 사용자의 완료된(isCompleted = true) 모든 할 일을 삭제
    @DeleteMapping("/todos/completed")
    public ResponseEntity<Void> clearCompletedTodos() {
        todoService.clearCompletedTodos();
        return ResponseEntity.noContent().build();
    }

}
