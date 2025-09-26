package com.example.cardatabase;

import com.example.cardatabase.web.CarController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CardatabaseApplicationTests {
	@Autowired		// 컨트롤러 객체가 만들어져서 필드로 들어감
	private CarController controller;

	@Test
	@DisplayName("First Example Test Case")
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
