package com.example.demo.web;

import com.example.demo.domain.Todo;
import com.example.demo.dto.TodoRequestDto;
import com.example.demo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos() {  // 전체 to-do를 가져오는 것이기 때문에 ResponstEntity의 제네릭 타입은List<To-do>로 가져옴
        return ResponseEntity.ok(todoService.getTodos());
    }

    @GetMapping("/users")
    public ResponseEntity<List<Todo>> getTodosCurrentUser() {
        return ResponseEntity.ok(todoService.getTodosForCurrentUser());
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody TodoRequestDto requestDto) {    // 하나만 작성하기 때문에 ResponstEntity의 제네릭 타입은 To-do 객체
        Todo createdTodo = todoService.createTodo(requestDto);
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }

    // 수정 / 삭제 파트에 해당하는 Controller 단계에서의 메서드 정의
    // 지금 수정 / 삭제 파트에서 DB에 있는 데이터를 수정 / 삭제하는 로직은 TodoService에 정의되어있음
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody TodoRequestDto updateDto) throws AccessDeniedException {
        return ResponseEntity.ok(todoService.updateTodoContent(id, updateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) throws AccessDeniedException {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();  // 이번엔 else 부분을 service에서 적용해줬기 때문에 cardatabase4에서 작성한 것과는 차이가 있음
    }

    // 토글 관련 메서드 정의
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Todo> toggleTodoStatus(@PathVariable Long id) throws AccessDeniedException {
        return ResponseEntity.ok(todoService.toggleTodoStatus(id));
    }

    // 일괄 삭제 메서드 정의
    @DeleteMapping("/completed")
    public ResponseEntity<Void> clearCompletedTodos() {
        todoService.clearCompletedTodos();
        return ResponseEntity.noContent().build();
    }

}
