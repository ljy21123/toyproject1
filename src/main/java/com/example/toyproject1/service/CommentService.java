package com.example.toyproject1.service;

import com.example.toyproject1.dto.CommentDto;
import com.example.toyproject1.entity.Article;
import com.example.toyproject1.entity.Comment;
import com.example.toyproject1.repository.ArticleRepository;
import com.example.toyproject1.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {

        /*// 댓글 조회
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        // 엔티티 -> DTO 변환
        List<CommentDto> dtos = new ArrayList<CommentDto>();

        for (Comment c : comments) {    // == for (int i = 0; i < comments.size(); i++) { Comment c = comments.get(i) }
            CommentDto dto = CommentDto.createCommentDto(c);
            dtos.add(dto);
        }*/

        // 댓글을 조회한 후 결과 반환
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(CommentDto::createCommentDto)  // 스트림의 각 요소(a)를 꺼내 b를 수행한 결과로 매핑
                .toList();                          // 스트림 데이터를 리스트 자료형으로 변환

    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {

        // 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() // 객체에 값이 존재하지 않으면 예외 발생
                        -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));

        // 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);

        // 댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);

        // DTO로 변환해 반환
        return CommentDto.createCommentDto(created);

    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {

        // 댓글 조회 및 예외 발생
        Comment inquiryComment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));

        // 댓글 수정
        inquiryComment.patch(dto);

        // DB로 갱신
        Comment updated = commentRepository.save(inquiryComment);

        // 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);

    }

    @Transactional
    public CommentDto delete(Long id) {

        // 댓글 조회 및 예외 발생
        Comment inquiryComment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));

        // 댓글 삭제
        commentRepository.delete(inquiryComment);

        // 삭제 댓글을 DTO로 변환 및 반환
        return CommentDto.createCommentDto(inquiryComment);

    }

}
