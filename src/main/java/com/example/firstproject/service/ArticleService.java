package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired // 의존성 주입 - 객체 자동 생성
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity(); // DTO를 엔티티로 변환
        if(article.getId() != null) { // 생성할 데이터에 id 값이 존재한다면
            return null; // null 값을 반환
        }
        return articleRepository.save(article); // DB에 저장
    }

    public Article update(Long id, ArticleForm dto) {
        // DTO -> 엔티티 변경하기
        Article article = dto.toEntity(); // 수정용 데이터가 담긴 엔티티
        log.info("id = {}, article = {}", id, article.toString());
        // 타깃 조회하기
        Article target = articleRepository.findById(id).orElse(null);
        // 잘못된 요청 처리하기
        if (target == null || article.getId() != id) {
            log.info("잘못된 요청! id : {}, article : {}", id, article.toString());
            return null;
        }
        // 업데이트 및 정상 응답하기
        target.patch(article); // 기존 데이터에 수정할 데이터 붙이기
        Article updated = articleRepository.save(target);
        return updated;
    }


    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);
        if (target == null) {
            return null; // .build()는 .body(null)과 동일한 결과
        }
        articleRepository.delete(target);
        return target;
    }


    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // 1. dto 묶음을 엔티티 묶음으로 변환하기
        List<Article> articleList = dtos.stream()// dto를 스트림화
                .map(dto->dto.toEntity()) // 하나하나를 엔티티로 매핑(연결)
                .collect(Collectors.toList()); // 결과들을 리스트로 묶기
        // 2. 엔티티 묶음을 DB에 저장하기
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
        // 3. 강제 예외 발생시키기
        articleRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("결제 실패!")); // 값이 존재하면 반환하고 없으면 전달값으로 보낸 예외를 발생시킨다\
        // 4. 결과 값 반환하기
        return articleList;
    }
}
