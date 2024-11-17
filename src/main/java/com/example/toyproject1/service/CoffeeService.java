package com.example.toyproject1.service;

import com.example.toyproject1.dto.CoffeeDto;
import com.example.toyproject1.entity.Coffee;
import com.example.toyproject1.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    public List<Coffee> index() {
        return coffeeRepository.findAll();
    }

    public Coffee show(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    public Coffee create(CoffeeDto dto) {

        Coffee coffee = dto.toEntity();

        if (coffee.getId() != null) {
            return null;
        }

        return coffeeRepository.save(coffee);

    }


    public Coffee update(Long id, CoffeeDto dto) {

        Coffee coffee = dto.toEntity();
        log.info("id: {}, coffee: {}", id, coffee.toString());

        Coffee existCoffee = coffeeRepository.findById(id).orElse(null);

        if (existCoffee == null || !id.equals(coffee.getId())) {
            log.info("잘못된 요청! id: {}, coffee: {}", id, coffee.toString());
            return null;
        }

        existCoffee.patch(coffee);
        return coffeeRepository.save(existCoffee);

    }

    public Coffee delete(Long id) {

        Coffee inquiryCoffee = coffeeRepository.findById(id).orElse(null);

        if (inquiryCoffee == null) {
            return null;
        }

        coffeeRepository.delete(inquiryCoffee);
        return inquiryCoffee;

    }
}
