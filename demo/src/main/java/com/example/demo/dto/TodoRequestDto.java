package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoRequestDto {       // front 상에서 input 창에 작성한 content를 받아옴
    private String content;
}
