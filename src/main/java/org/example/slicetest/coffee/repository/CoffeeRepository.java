package org.example.slicetest.coffee.repository;

import java.util.Optional;
import org.example.slicetest.coffee.domain.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {


    Optional<Coffee> findByName(String name);
}
