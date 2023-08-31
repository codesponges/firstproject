package com.example.firstproject.dto;


import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleForm {


    private Long id; // Long은 클래스 타입이다. 따라서 기본값이 null이다. 정수형 클래스 타입인 long과 다르다
    private String title;
    private String content;


    public Article toEntity() {
        return new Article(id, title, content);
    }
}
