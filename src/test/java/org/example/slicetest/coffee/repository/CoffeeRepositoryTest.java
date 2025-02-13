package org.example.slicetest.coffee.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.example.slicetest.IntegrationTestSupport;
import org.example.slicetest.coffee.domain.Coffee;
import org.example.slicetest.coffee.service.request.CoffeeCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class CoffeeRepositoryTest extends IntegrationTestSupport {

    @Autowired
    CoffeeRepository coffeeRepository;

    @Test
    @DisplayName("이름으로 커피를 조회할 때 커피가 존재하면 반환한다")
    void findByName_returnsCoffee_whenCoffeeExists() {
        //given
        Coffee coffee = Coffee.create(new CoffeeCreateRequest("Latte", 4000));
        coffeeRepository.save(coffee);

        //when
        Optional<Coffee> foundCoffee = coffeeRepository.findByName("Latte");

        //then
        assertThat(foundCoffee)
            .isPresent()
            .get()
            .extracting("name", "price")
            .containsExactly("Latte", 4000);
    }

    @Test
    @DisplayName("이름으로 커피를 조회할 때 커피가 존재하지 않으면 빈 Optional을 반환한다")
    void findByName_returnsEmptyOptional_whenCoffeeDoesNotExist() {
        //when
        Optional<Coffee> foundCoffee = coffeeRepository.findByName("NonExistentCoffee");

        //then
        assertThat(foundCoffee).isNotPresent();
    }
}