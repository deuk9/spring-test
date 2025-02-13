package org.example.slicetest.coffee.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.slicetest.ControllerTestSupport;
import org.example.slicetest.coffee.service.CoffeeService;
import org.example.slicetest.coffee.service.request.CoffeeCreateRequest;
import org.example.slicetest.coffee.service.response.CoffeeDetailResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

class CoffeeControllerTest extends ControllerTestSupport {

    @MockitoBean
    CoffeeService coffeeService;

    @Test
    @DisplayName("유효한 요청으로 커피를 생성한다")
    void create_createsCoffee_whenValidRequest() throws Exception {
        CoffeeCreateRequest request = new CoffeeCreateRequest("Latte", 4000);

        mockMvc.perform(post("/coffees")
                .contentType("application/json")
                .content("{\"name\":\"Latte\",\"price\":4000}"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("유효한 ID로 커피를 조회한다")
    void getById_returnsCoffee_whenValidId() throws Exception {
        CoffeeDetailResponse response = new CoffeeDetailResponse(1L, "Latte", 4000);
        when(coffeeService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/coffees/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Latte"))
            .andExpect(jsonPath("$.price").value(4000));
    }

    @Test
    @DisplayName("유효한 이름으로 커피를 조회한다")
    void getByName_returnsCoffee_whenValidName() throws Exception {
        CoffeeDetailResponse response = new CoffeeDetailResponse(1L, "Latte", 4000);
        when(coffeeService.getByName("Latte")).thenReturn(response);

        mockMvc.perform(get("/coffees")
                .param("name", "Latte"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Latte"))
            .andExpect(jsonPath("$.price").value(4000));
    }

    @Test
    @DisplayName("유효하지 않은 ID로 커피를 조회할 때 404를 반환한다")
    void getById_returnsNotFound_whenInvalidId() throws Exception {
        when(coffeeService.getById(999L)).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/coffees/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("유효하지 않은 이름으로 커피를 조회할 때 404를 반환한다")
    void getByName_returnsNotFound_whenInvalidName() throws Exception {
        when(coffeeService.getByName("NonExistentCoffee")).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/coffees")
                .param("name", "NonExistentCoffee"))
            .andExpect(status().isNotFound());
    }
}