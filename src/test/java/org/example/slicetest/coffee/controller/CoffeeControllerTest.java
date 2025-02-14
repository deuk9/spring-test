package org.example.slicetest.coffee.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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
    private CoffeeService coffeeService;


    @Test
    @DisplayName("유효한 요청으로 커피를 생성한다")
    void create_createsCoffee_whenValidRequest() throws Exception {
        //given
        CoffeeCreateRequest request = new CoffeeCreateRequest("Latte", 4000);

        //then
        mockMvc.perform(post("/coffees")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(document("coffees/create",
                requestFields(
                    fieldWithPath("name")
                        .type(STRING)
                        .description("The name of the coffee"),
                    fieldWithPath("price")
                        .type(NUMBER)
                        .description("The price of the coffee")
                )));
    }

    @Test
    @DisplayName("유효한 ID로 커피를 조회한다")
    void getById_returnsCoffee_whenValidId() throws Exception {
        CoffeeDetailResponse response = new CoffeeDetailResponse(1L, "Latte", 4000);
        given(coffeeService.getById(1L)).willReturn(response);

        mockMvc.perform(get("/coffees/{id}", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Latte"))
            .andExpect(jsonPath("$.price").value(4000))
            .andDo(document("coffees/get-by-id",
                pathParameters(
                    parameterWithName("id").description("커피 아이디")
                ),
                responseFields(
                    fieldWithPath("id").type(NUMBER).description("The ID of the coffee"),
                    fieldWithPath("name").type(STRING).description("The name of the coffee"),
                    fieldWithPath("price").type(NUMBER).description("The price of the coffee")
                )));
    }

    @Test
    @DisplayName("유효한 이름으로 커피를 조회한다")
    void getByName_returnsCoffee_whenValidName() throws Exception {
        CoffeeDetailResponse response = new CoffeeDetailResponse(1L, "Latte", 4000);
        given(coffeeService.getByName("Latte")).willReturn(response);

        mockMvc.perform(get("/coffees")
                .param("name", "Latte"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Latte"))
            .andExpect(jsonPath("$.price").value(4000))
            .andDo(document("coffees/get-by-name",
                responseFields(
                    fieldWithPath("id").type(NUMBER).description("The ID of the coffee"),
                    fieldWithPath("name").type(STRING).description("The name of the coffee"),
                    fieldWithPath("price").type(NUMBER).description("The price of the coffee")
                )));
    }

    @Test
    @DisplayName("유효하지 않은 ID로 커피를 조회할 때 404를 반환한다")
    void getById_returnsNotFound_whenInvalidId() throws Exception {
        given(coffeeService.getById(999L)).willThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/coffees/{id}", 999))
            .andExpect(status().isNotFound())
            .andDo(document("coffees/get-by-id-not-found"));
    }

    @Test
    @DisplayName("유효하지 않은 이름으로 커피를 조회할 때 404를 반환한다")
    void getByName_returnsNotFound_whenInvalidName() throws Exception {
        given(coffeeService.getByName("NonExistentCoffee")).willThrow(
            IllegalArgumentException.class);

        mockMvc.perform(get("/coffees")
                .param("name", "NonExistentCoffee"))
            .andExpect(status().isNotFound())
            .andDo(document("coffees/get-by-name-not-found"));
    }
}