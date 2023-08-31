package com.example.firstproject.service;


import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository; // 댓글 생성 시 대상 게시글의 존재 여부를 판단할 수 있기 때문에


    public List<CommentDto> comments(Long articleId) {
        /*// 1. 댓글 조회
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        // 2. 엔티티 -> DTO 반환
        List<CommentDto> dtos = new ArrayList<CommentDto>(); // 비어있는 CommentDto타입의 리스트 생성
        for(int i=0; i<comments.size(); i++){
            Comment c = comments.get(i);
            CommentDto dto = CommentDto.createCommentDto(c);
            dtos.add(dto);
        }*/
        // 3. 결과 반환
        return commentRepository.findByArticleId(articleId) // 댓글 엔티티 목록을 조회
                 .stream() // 댓글 엔티티 목록을 스트림으로 변환
                .map(comment -> CommentDto.createCommentDto(comment)) // 엔티티를 DTO로 매핑
                .collect(Collectors.toList()); // 스트림을 리스트로 변환
    }

    @Transactional // create() 메서드는 DB의 내용을 변경하기 때문에 실패할 경우를 대비해 트랜잭션 설정으로 롤백 할 대비를 해야한다
    public CommentDto create(Long articleId, CommentDto dto) {
        // 1. 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId) // 댓글 생성할 부모 게시글 가져오기
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! " +
                        "대상 게시글이 없습니다.")); // 댓글 게시글이 없는 경우에는 댓글을 생성할 수 없다
        // 2. 댓글 엔티티 생성 - 댓글 dto와 게시글 엔티티를 이용해서 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);
        // 3. 댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);
        // 4. DTO로 변환해 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! " +
                       "대상 댓글이 없습니다." ));
        // 2. 댓글 수정
        target.patch(dto);
        // 3. DB로 갱신
        Comment updated = commentRepository.save(target);
        // 4. 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    public CommentDto delete(Long id) {
        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! " +
                        "삭제할 댓글이 없습니다"));
        // 2. DB에서 삭제
        commentRepository.delete(target);
        // 3. 삭제 댓글을 DTO로 변환 및 반환
        return CommentDto.createCommentDto(target);
    }
}














