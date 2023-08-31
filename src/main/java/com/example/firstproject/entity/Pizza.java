package com.example.firstproject.entity;

import com.example.firstproject.dto.PizzaForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

@Entity // 엔티티 클래스를 기반으로, DB에 테이블을 생성한다
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 자동으로 ID 생성 - 데이터 생성 시 입력값으로 id 넣으면 오류 발생
    private Long id;
    @Column
    private String name;
    @Column
    private double price;

    public void patch(PizzaForm dto) {
        if(dto.getName() != null)
            this.name = dto.getName();
        if(dto.getPrice() != 0)
            this.price = dto.getPrice();
    }
}
