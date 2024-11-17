package com.example.toyproject1.repository;

import com.example.toyproject1.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {    // CrudRepository와 다르게 페이징 기능을 포함

    // 특정 게시글의 모든 댓글 조회 (네이티브 쿼리 메서드)
    // nativaQuery = 기존 SQL문을 사용
    @Query(value = "SELECT * FROM comment WHERE article_id = :articleId", nativeQuery = true)
    List<Comment> findByArticleId(Long articleId);

    // 특정 닉네임의 모든 댓글 조회 (네이티브 쿼리 XML)
    List<Comment> findByNickname(String nickname);

}
