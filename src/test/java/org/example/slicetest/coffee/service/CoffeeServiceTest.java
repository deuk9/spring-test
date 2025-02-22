package org.example.slicetest.coffee.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.NoSuchElementException;
import org.example.slicetest.support.IntegrationTestSupport;
import org.example.slicetest.coffee.service.request.CoffeeCreateRequest;
import org.example.slicetest.coffee.service.response.CoffeeDetailResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class CoffeeServiceTest extends IntegrationTestSupport {

    @Autowired
    CoffeeService coffeeService;

    @Test
    @DisplayName("유효한 요청일 때 커피를 저장한다")
    void create_savesCoffee_whenValidRequest() {
        //given
        CoffeeCreateRequest request = new CoffeeCreateRequest("Latte", 4000);

        //when
        coffeeService.create(request);

        assertThat(coffeeService.getByName("Latte"))
            .isNotNull()
            .extracting("name", "price")
            .containsExactly("Latte", 4000);
    }


    @Test
    @DisplayName("ID로 커피를 조회할 때 커피가 존재하지 않으면 예외를 던진다")
    void getById_throwsException_whenCoffeeNotFound() {
        //when & then
        assertThatThrownBy(() -> coffeeService.getById(999L))
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("이름으로 커피를 조회할 때 커피가 존재하지 않으면 예외를 던진다")
    void getByName_throwsException_whenCoffeeNotFound() {
        //when & then
        assertThatThrownBy(() -> coffeeService.getByName("NonExistentCoffee"))
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("중복된 이름으로 커피를 생성할 때 예외를 던진다")
    void create_throwsException_whenDuplicateName() {
        //given
        CoffeeCreateRequest request1 = new CoffeeCreateRequest("Mocha", 5000);
        CoffeeCreateRequest request2 = new CoffeeCreateRequest("Mocha", 6000);
        coffeeService.create(request1);

        //when & then
        assertThatThrownBy(() -> coffeeService.create(request2))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("유효하지 않은 ID로 커피를 삭제할 때 예외를 던진다")
    void delete_throwsException_whenInvalidId() {
        //when & then
        assertThatThrownBy(() -> coffeeService.delete(999L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("유효한 이름으로 커피를 조회한다")
    void getByName_returnsCoffee_whenValidName() {
        //given
        CoffeeCreateRequest request = new CoffeeCreateRequest("Cappuccino", 4500);
        coffeeService.create(request);

        //when
        CoffeeDetailResponse coffee = coffeeService.getByName("Cappuccino");

        //then
        assertThat(coffee)
            .isNotNull()
            .extracting("name", "price")
            .containsExactly("Cappuccino", 4500);
    }


}