package org.example.slicetest.coffee.service.response;


import lombok.AccessLevel;
import lombok.Builder;
import org.example.slicetest.coffee.domain.Coffee;

@Builder(access = AccessLevel.PRIVATE)
public record CoffeeDetailResponse(
    Long id,
    String name,
    Integer price
) {


    public static CoffeeDetailResponse from(Coffee coffee) {
        return CoffeeDetailResponse.builder()
            .id(coffee.getId())
            .name(coffee.getName())
            .price(coffee.getPrice())
            .build();
    }

}
