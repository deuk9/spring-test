package org.example.slicetest.coffee.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.slicetest.coffee.service.request.CoffeeCreateRequest;

@Entity
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Coffee {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer price;


    public static Coffee create(CoffeeCreateRequest request) {
        return Coffee.builder()
            .name(request.name())
            .price(request.price())
            .build();
    }
}
