package com.example.toyproject1.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private int price;

    public void patch(Coffee coffee) {
        if (coffee.name != null) {
            this.name = coffee.name;
        }
        if (coffee.price != 0) {
            this.price = coffee.price;
        }
    }

}
