package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest // 해당 클래스를 스프링 부트와 연동해 통합 테스트를 수행한다
class ArticleServiceTest {
    @Autowired // 의존성 주입 - 자동 객체 생성
    private ArticleService articleService;

    @Transactional
    @Test // 해당 메서드는 테스트를 위한 코드입니다
    void index() {
        // 1, 예상 데이터
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L,"다다다다", "3333");
        List<Article> expected = Arrays.asList(a,b,c);
        // 2. 실제 데이터
        List<Article> articleList = articleService.index();
        // 3. 비교 및 검증
        assertEquals(expected.toString(), articleList.toString());

    }
    @Transactional
    @Test
    void show_성공_존재하는_id_입력() {
        // 1. 예상 데이터
            Long id = 1L;
            Article expected = new Article(id, "가가가가", "1111");
        // 2. 실제 데이터
            Article article = articleService.show(id);
        // 3. 비교 및 검증
            assertEquals(expected.toString(), article.toString());
    }
    @Transactional
    @Test
    void show_실패() {
        // 1. 예상 데이터
            Long id = 100L;
            Article expected = null;
        // 2. 실제 데이터
            Article article = articleService.show(id);
        // 3. 비교 및 검증
        assertEquals(expected, article); // null 값은 toString() 메서드를 사용하지 못한다
    }

    @Transactional
    @Test
    void create_성공_title과_content만_있는_dto_입력() {
        // 1. 예상 데이터
        String title = "가가가가";
        String content = "1111";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);
        // 2. 실제 데이터
        Article article = articleService.create(dto);
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());

    }
    @Transactional
    @Test
    void create_실패_id가_포함된_dto_입력() {
        // 1. 예상 데이터
        Long id = 1L;
        String title = "가가가가";
        String content = "1111";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;
        // 2. 실제 데이터
        Article article = articleService.create(dto);
        // 3. 비교 및 검증
        assertEquals(expected, article);
    }
    @Transactional
    @Test
    void update_성공_존재하는_id와_title_content가_있는_dto_입력() {
        // 1 예상 데이터
        Long id = 1L;
        String title = "나나나나";
        String content = "1111";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(id, title, content);
        // 2 실제 데이터
        Article updated = articleService.update(id, dto);
        // 3 비교 및 검증
        assertEquals(expected.toString(), updated.toString());
    }
    @Transactional
    @Test
    void update_성공_존재하는_id와_title만_있는_dto_입력() {
        Long id = 1L;
        String title = "나나나나";
        ArticleForm dto = new ArticleForm(id, title, null);
        Article expected = new Article(1L, "나나나나", "1111");
        // 2 실제 데이터
        Article updated = articleService.update(id, dto);
        // 3 비교 및 검증
        assertEquals(expected.toString(), updated.toString());
    }
    @Transactional
    @Test
    void update_실패_존재하지_않는_id의_dto_입력() {
        Long id = 4L;
        String title = "나나나나";
        String content = "2222";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;
        // 2 실제 데이터
        Article updated = articleService.update(id, dto);
        // 3 비교 및 검증
        assertEquals(expected, updated);
    }

    @Transactional
    @Test
    void delete_성공_존재하는_id_입력() {
        // 1, 예상 데이터
        Long id = 1L;
        String title = "가가가가";
        String content = "1111";
        Article expected = new Article(id, title, content);
        // 2. 실제 데이터
        Article deleted = articleService.delete(id);
        // 3. 비교 및 검증
        assertEquals(expected.toString(), deleted.toString());

    }
    @Transactional
    @Test
    void delete_실패_존재하지_않는_id_입력() {
        // 1. 예상 데이터
        Long id = 4L;
        Article expected = null;
        // 2. 실제 데이터
        Article deleted = articleService.delete(id);
        // 3. 비교 및 검증
        assertEquals(expected, deleted);
    }
}