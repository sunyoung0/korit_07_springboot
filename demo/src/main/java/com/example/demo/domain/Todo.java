package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String content;

    private boolean isCompleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // 생성자에서 content와 user를 받음. 근데 글 작성할 때 input 창에서 content만 일단 받아오기 때문에 생성자를 만들 수 없음.
    // 그래서 content와 user를 합쳐주는(조립하는) 단계가 필요함. -> service에서 작성
    // service에서 처음으로 새로운 to-do 객체를 생성함.
    public Todo(String content, User user) {
        this.content = content;
        this.user = user;
    }
}
