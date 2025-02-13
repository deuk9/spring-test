package org.example.slicetest.coffee.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.slicetest.coffee.domain.Coffee;
import org.example.slicetest.coffee.repository.CoffeeRepository;
import org.example.slicetest.coffee.service.request.CoffeeCreateRequest;
import org.example.slicetest.coffee.service.response.CoffeeDetailResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;

    public List<CoffeeDetailResponse> getAll() {
        return coffeeRepository.findAll().stream()
            .map(CoffeeDetailResponse::from)
            .toList();
    }

    public CoffeeDetailResponse getById(Long id) {
        return coffeeRepository.findById(id)
            .map(CoffeeDetailResponse::from)
            .orElseThrow();
    }

    public CoffeeDetailResponse getByName(String name) {
        return coffeeRepository.findByName(name)
            .map(CoffeeDetailResponse::from)
            .orElseThrow();
    }

    @Transactional
    public void create(CoffeeCreateRequest coffeeCreateRequest) {

        coffeeRepository.findByName(coffeeCreateRequest.name())
            .ifPresent(coffee -> {
                throw new IllegalArgumentException("Coffee already exists");
            });

        coffeeRepository.save(Coffee.create(coffeeCreateRequest));
    }

    @Transactional
    public void delete(Long id) {
        Coffee coffee = coffeeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Coffee not found"));

        coffeeRepository.delete(coffee);
    }
}
