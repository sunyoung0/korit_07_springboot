package com.example.cardatabase;

import com.example.cardatabase.domain.CarRepository;
import com.example.cardatabase.web.CarController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarRepository carRepository;

    @Test
    @DisplayName("GET /cars 요청 테스트 검증")
    void testGetCars() throws Exception {

        this.mockMvc.perform(get("/cars")).andExpect(status().isOk());


        //        this.mockMvc.perform(post("/login")
//                        .content("{\"username\":\"admin\",\"password\":\"admin\"}")
//                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
//                .andDo(print()).andExpect(status().isOk());
    }

}
