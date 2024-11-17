package com.example.toyproject1.dto;

import com.example.toyproject1.entity.Coffee;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class CoffeeDto {

    private Long id;
    private String name;
    private int price;

    public Coffee toEntity() {
        return new Coffee(id, name, price);
    }

}
