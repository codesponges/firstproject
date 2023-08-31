package com.example.firstproject.service;

import com.example.firstproject.dto.PizzaForm;
import com.example.firstproject.entity.Pizza;
import com.example.firstproject.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {

    // 서비스에서는 DB와 연동되는 작업들을 수행하기 때문에 만일에 대비해 @Transactional 에너테이션을 붙이는 습관을 들일 것

    @Autowired // 의존성 주입 객체 자동 생성
    private PizzaRepository pizzaRepository;

    public List<Pizza> index() {
        return pizzaRepository.findAll();
    }


    public Pizza show(Long id) {
        return pizzaRepository.findById(id).orElse(null);
    }

    public Pizza create(PizzaForm dto) {
        // DTO를 엔티티로 변환
        Pizza pizza = dto.toEntity();
        // 기존 데이터 덮어쓰기 방지
        if(pizza.getId() != null){
            return null;
        }
        // DB에 저장
        Pizza created = pizzaRepository.save(pizza); // DB에 저장할 때 id 생성
        // 결과 반환
        return created;
    }

    public Pizza delete(Long id) {
        // 메뉴 조회 및 에외 처리
        Pizza target = pizzaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("메뉴 삭제 실패! " +
                        "삭제할 메뉴가 존재하지 않습니다"));
        // DB에서 삭제
        pizzaRepository.delete(target);
        // 결과 반환
        return target;
    }

    public Pizza update(Long id, PizzaForm dto) {
        // 해당하는 주문번호 id에 주문한 메뉴가 있는지 확인
        Pizza target = pizzaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("메뉴 변경 실패! " +
                        "변경할 메뉴가 존재하지 않습니다."));
        if(id != dto.getId() || target == null){
            throw new IllegalArgumentException("변경할 주문번호와 요청한 주문번호가 불일치 하거나 변경할 메뉴가 없습니다");
        }
        // 메뉴 변경 - 기존에서 갱신할 부분만 변경
        target.patch(dto);
        // 변경한 메뉴를 DB에 갱신
        Pizza updated = pizzaRepository.save(target);
        // 결과 반환
        return updated;
    }
}
