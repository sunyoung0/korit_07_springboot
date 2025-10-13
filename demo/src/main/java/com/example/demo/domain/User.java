package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")      // User가 예약어라서 나중에 문제가 생길 수 있으므로 데이터베이스 상에서의 테이블 명을 따로 지정 해줌
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)     // nullable : null 방지 ,updatable : 함부로 id 값을 바꿀 수 없음
    private Long id;

    @Column(nullable = false, unique = true)        // 겹치면 안되기 때문에 unique 설정
    private String username;

    @Column(nullable = false)                       // 겹쳐도 상관 없으니까 unique 설정 없어도 됨
    private String password;

    @Column(nullable = false)
    private String role;

    // id 하나 당 다수의 투두를 가질 수 있음
    // cascade = CascadeType.ALL : User가 저장/삭제될 때 연관된 투두도 같이 처리 (예: User 삭제 시 투두도 삭제)
    // orphanRemoval = true	todos : 리스트에서 투두 객체가 제거되면 DB에서도 삭제됨
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todo> todos = new ArrayList<>();

    // id는 autoIncrement 이므로 생성자에 추가할 필요 없음, 투두는 비어있는 상태이므로 굳이 생성자에 추가할 필요가 없음
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
