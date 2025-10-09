package com.example.todolist.service;

import com.example.todolist.domain.AppUserRepository;
import com.example.todolist.domain.Todo;
import com.example.todolist.domain.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final AppUserRepository appUserRepository;

    public TodoService(TodoRepository todoRepository, AppUserRepository appUserRepository) {
        this.todoRepository = todoRepository;
        this.appUserRepository = appUserRepository;
    }

    // Todo 전체 조회
    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    // Todo id 별 조회
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    // Todo 작성
    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    // Todo 내용 수정
    @Transactional
    public Optional<Todo> updateTodo(Long id, Todo todoDetails) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setContent(todoDetails.getContent());
                    return todo;
                });
    }

    // Todo 하나 삭제
    public boolean deleteTodo(Long id) {
        if(todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Todo 완료 상태 변경 updateTodoStatus
    public Optional<Todo> updateTodoStatus(Long id) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setCompleted(!todo.isCompleted());
                    return todoRepository.save(todo);
                });
    }

    // clearCompletedTodos
    public boolean clearCompletedTodos(Long id) {
        return true;
    }
    /**
     - **`clearCompletedTodos()`**: 현재 로그인된 사용자의 완료된(`isCompleted = true`) 모든 할 일을 삭제합니다.

     - **로직**: 현재 인증된 사용자의 `id`를 가져옵니다. 해당 사용자의 `Todo` 목록 중 `isCompleted`가 `true`인 항목들을 모두 찾아서 한 번에 삭제합니다.
     */

}
