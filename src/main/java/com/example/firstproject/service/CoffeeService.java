package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeForm;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class CoffeeService {
    @Autowired // 의존성 주입 - 객체 자동 생성
    private CoffeeRepository coffeeRepository;


    public List<Coffee> index() {
        return coffeeRepository.findAll();
    }


    public Coffee show(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    public Coffee create(CoffeeForm dto) {
        // DTO를 엔티티로 변환
        Coffee coffee = dto.toEntity(); // 엔티티로 변환되는 순간에 id는 null 값이 들어간다
        if(coffee.getId() != null) {
            return null;
        }
        // DB에 저장
        Coffee created = coffeeRepository.save(coffee);
        // 결과값 반환
        return created;
    }

    public Coffee update(Long id, CoffeeForm dto) {
        // 수정 데이터 엔티티 생성하기
        Coffee coffee = dto.toEntity();
        // 기존에 존재하는 id에 대응하는 데이터 찾기
        Coffee target = coffeeRepository.findById(id).orElse(null);
        // 잘못된 요청 처리하기
        if(target == null || id != coffee.getId()) {
            log.info("id : {}, coffee : {}", id, coffee.toString());
            return null;
        }
        // 정상적인 결과 반환
        target.patch(coffee); // 수정하려고 넘긴 데이터에 해당하는 내용만 수정할 수 있도록 하는 메서드
        Coffee updated = coffeeRepository.save(target);
        return updated;
    }

    public Coffee delete(Long id) {
        // 삭제할 id에 해당하는 엔티티 찾기
        Coffee target = coffeeRepository.findById(id).orElse(null);
        // 잘못된 요청 처리
        if(target == null) {
            log.info("잘못된 요청! 삭제할 데이터가 없습니다");
            return null;
        }
        coffeeRepository.delete(target); // DB에서 삭제하고
        return target; // 삭제한 데이터 반환
    }
}
