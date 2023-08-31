package com.example.firstproject.entity;


import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 ID를 자동으로 생성
    private Long id;

    @ManyToOne // Article 테이블과 다대일 관계
    @JoinColumn(name="article_id") // 외래키 생성, Article 엔티티의 기본키(id)와 매핑
    private Article article;

    @Column // 해당 필드를 테이블의 속성으로 매핑
    private String nickname;

    @Column // 해당 필드를 테이블의 속성으로 매핑
    private String body;


    public static Comment createComment(CommentDto dto, Article article) {
        // 예외 발생
        if(dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id를 입력하면 안됩니다");
        if(dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 싫패! 요청 URL의 ID와 게시글 ID가 일치하지 않습니다");
        // 엔티티 생성 및 반환
        return new Comment(
                dto.getId(), // 댓글 아이디 - null이 넘어오겠지
                article, // 부모 게시글
                dto.getNickname(), // 댓글 닉네임
                dto.getBody() // 댓글 본문
        );


    }

    public void patch(CommentDto dto) {
        // 예외 발생
        if(this.id != dto.getId())
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다");
        // 객체 갱신
        if(dto.getNickname() != null) // 수정할 닉네임 데이터가 있다면
            this.nickname = dto.getNickname(); // 내용 반영
        if(dto.getBody() != null) // 수정할 본문 데이터가 있다면
            this.body = dto.getBody(); // 내용 반영

    }
}
