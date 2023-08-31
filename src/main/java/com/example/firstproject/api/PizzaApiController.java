package com.example.firstproject.api;

import com.example.firstproject.dto.PizzaForm;
import com.example.firstproject.entity.Pizza;
import com.example.firstproject.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PizzaApiController {
    @Autowired // 의존성 주입 - 객체 자동 생성
    private PizzaService pizzaService;


    @GetMapping("/api/pizzas") // 피자 목록 보여주기
    public List<Pizza> index() {
        return pizzaService.index();
    }


    @GetMapping("/api/pizzas/{id}") // 단일 피자 목록 보여주기
    public Pizza show(@PathVariable Long id) {
        return pizzaService.show(id);
    }

    @PostMapping("/api/pizzas")
    public ResponseEntity<Pizza> create(@RequestBody PizzaForm dto) {
        // 서비스에 위임
        Pizza created = pizzaService.create(dto);
        // 결과 반환 - 무조건 성공으로 가정
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @PatchMapping("/api/pizzas/{id}")
    public ResponseEntity<Pizza> update(@PathVariable Long id,
                                        @RequestBody PizzaForm dto){
        // 서비스에 위임
        Pizza updated = pizzaService.update(id, dto);
        // 결과 반환
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @DeleteMapping("/api/pizzas/{id}")
    public ResponseEntity<Pizza> delete(@PathVariable Long id){
        // 서비스에 위임
        Pizza deleted = pizzaService.delete(id);
        // 결과 반환
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }


}
