package com.example.toyproject1.repository;

import com.example.toyproject1.entity.Coffee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
    @Override
    List<Coffee> findAll();
}
