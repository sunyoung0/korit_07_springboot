package com.example.demo.service;

import com.example.demo.domain.Todo;
import com.example.demo.domain.TodoRepository;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import com.example.demo.dto.TodoRequestDto;
import com.example.demo.dto.TodoRequestRecord;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class TodoService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public TodoService(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    private User getCurrentUser() { // 현재 로그인 한 유저를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User를 찾을 수 없습니다."));
    }

    // 본인 확인 로직을 추가. To-do 객체 내에 있는 User와, 지금 요청을 보내는 User가 동일한 지를 확인
    private void checkOwnerShip(Todo todo) throws AccessDeniedException {   // to-do를 매개변수로 가져와서 거기 있는 user를 가져옴. 그 user와 로그인 유저가 동일한 지 확인
        if(!todo.getUser().equals(getCurrentUser())) {
            throw new AccessDeniedException("해당 todo에 접근할 수 없습니다.");
        }
    }

    // 1. 사용자 구분 없이 모든 할 일 목록 조회
    @Transactional      // 순서 중에 어긋나면 rollback이 알아서 일어남
    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    // 2. 현재 사용자의 모든 할 일 목록 조회
    @Transactional(readOnly = true) // 읽기만 허용
    public List<Todo> getTodosForCurrentUser() {
        User currentUser = getCurrentUser();
        return todoRepository.findByUserId(currentUser.getId());
    }

    // 3. 새로운 to-do 추가 - dto 버전
    public Todo createTodo(TodoRequestDto todoRequestDto) {
        User currentUser = getCurrentUser();    // 현재 로그인 한 유저
        Todo newTodo = new Todo(todoRequestDto.getContent(), currentUser);      // 객체 생성

        return todoRepository.save(newTodo);
    }

      // 3-1. 새로운 to-do 추가 - record 버전
//    public Todo createTodo2(TodoRequestRecord todoRequestRecord) {
//        User currentUser = getCurrentUser();
//        Todo newTodo2 = new Todo(todoRequestRecord.content(), currentUser);
//
//        return todoRepository.save(newTodo2);
//    }

    // 4. 할 일 내용 수정
    @Transactional
    public Todo updateTodoContent(Long id, TodoRequestDto updateDto) throws AccessDeniedException {  // AccessDeniedException - 접근 불가, 본인이 작성한 것이 아닐 경우
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 todo가 없습니다. id : " + id));
        checkOwnerShip(todo);   // 위에 id로 to-do를 찾아옴 그 to-do 객체를 checkOwnerShip()에 넣어서 User가 동일한 지 체크함

        todo.setContent(updateDto.getContent());    // setContent는 String이라서 updateDto는 객체라 자료형이 안맞음. get 해서 content를 가져와야함
        return todoRepository.save(todo);
    }

    // 5. 할 일 삭제
    @Transactional
    public void deleteTodo(Long id) throws AccessDeniedException {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 todo가 없습니다. id : " + id));
        checkOwnerShip(todo);

        todoRepository.delete(todo);
    }

    // 6. 할 일 완료 상태 토글
    @Transactional
    public Todo toggleTodoStatus(Long id) throws AccessDeniedException {
        // id 매개변수가 있다면 시작 코드는 id에 해당하는 to-do 객체를 가져오고 본인확인
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 todo가 없습니다. id : " + id));
        checkOwnerShip(todo);

        todo.setCompleted(!todo.isCompleted());
        return todoRepository.save(todo);
    }

    // 7. 완료된 할 일 전체를 삭제하는 로직
    @Transactional
    public void clearCompletedTodos() {
        User currentUser = getCurrentUser();
        todoRepository.deleteByUserAndIsCompleted(currentUser, true);   // 완료된 것만 삭제해야 하기 때문에 두번째 매개변수는 true
    }

}
