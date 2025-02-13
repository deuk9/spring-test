package org.example.slicetest.coffee.controller;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.slicetest.coffee.service.CoffeeService;
import org.example.slicetest.coffee.service.request.CoffeeCreateRequest;
import org.example.slicetest.coffee.service.response.CoffeeDetailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coffees")
public class CoffeeController {

    private final CoffeeService coffeeService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CoffeeCreateRequest coffeeCreateRequest) {
        coffeeService.create(coffeeCreateRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<CoffeeDetailResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(coffeeService.getById(id));
    }

    @GetMapping
    public ResponseEntity<CoffeeDetailResponse> getByName(@RequestParam String name) {
        return ResponseEntity.ok(coffeeService.getByName(name));
    }
}
