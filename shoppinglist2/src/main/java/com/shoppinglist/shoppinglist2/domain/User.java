package com.shoppinglist.shoppinglist2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, updatable = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore // 비밀번호가 API 응답에 노출되지 않도록 함. 어차피 암호화 할 것이기 때문.
    private String password;

    @Column(nullable = false)
    private String role;

    // 한 명의 유저는 여러 개의 쇼핑 아이템을 가질겁니다.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // car에서처럼 순환참조를 일으키지 않도록 했습니다.
    private List<ShoppingItem> items;

    // CommandLineRunner 상에서 사용할 간단한 생성자 정의 하겠습니다.
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
