package com.example.firstproject.dto;

import com.example.firstproject.entity.Pizza;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class PizzaForm {

    private Long id;
    private String name;
    private double price;


    public Pizza toEntity() { // DTO를 엔티티로 만드는 메서드
        return new Pizza(id, name, price);
    }
}
