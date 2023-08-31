package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString // 모든 필드를  출력할 수 있는 toString 메서드 자동 생성
@Getter
public class CommentDto {
    private Long id; // 댓글 id
    private Long articleId; // 댓글의 부모 id = 게시글 id
    private String nickname; // 댓글 작성자
    private String body; // 댓글 본문








    public static CommentDto createCommentDto(Comment comment) { // 정적 메서드. 클래스로 바로 호출하는 메서드
        return new CommentDto(
                comment.getId(),
                comment.getArticle().getId(), // 댓글 엔티티가 속한 부모 게시글의 id
                comment.getNickname(), // 댓글 엔티티의 nickname
                comment.getBody() // 댓글 엔티티의 body
        );
    }



}




























