package com.example.todolist.service;

import com.example.todolist.domain.AppUser;
import com.example.todolist.domain.AppUserRepository;
import com.example.todolist.domain.Todo;
import com.example.todolist.domain.TodoRepository;
import com.example.todolist.dto.TodoRequestRecord;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
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

    // 현재 로그인 한 유저 정보
    private AppUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return appUserRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User를 찾을 수 없습니다."));
    }

    // 본인 확인 로직을 추가.
    private void checkOwnerShip(Todo todo) throws AccessDeniedException {
        if(!todo.getAppuser().equals(getCurrentUser())) {
            throw new AccessDeniedException(("해당 todo에 접근할 수 없습니다."));
        }
    }

    // Todo 전체 조회
    @Transactional
    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    // Todo id 별 조회
    @Transactional
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    // Todo 작성
    public Todo addTodo(TodoRequestRecord todoRecord) {
        AppUser currentUser = getCurrentUser();
        Todo newTodo = new Todo(todoRecord.content(), currentUser);
        return todoRepository.save(newTodo);
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

    // 완료된 할 일 전체를 삭제하는 로직
    public void clearCompletedTodos() {
        AppUser currentUser = getCurrentUser();
        todoRepository.deleteByAppuserAndIsCompleted(currentUser, true);
    }

}
