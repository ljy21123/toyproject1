package com.example.toyproject1.entity;

import com.example.toyproject1.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne                      // Comment 엔티티와 Article 엔티티를 다대일 관계로 설정
    @JoinColumn(name="article_id")  // 외래키 생성, Article 엔티티의 기본키(id)와 매핑
    private Article article;        // 해당 댓글의 부모 게시글

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {
        
        // 예외 발생
        if (dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        if (!Objects.equals(dto.getArticleId(), article.getId()))
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐습니다.");
        
        // 엔티티 생성 및 반환
        return new Comment(
                null,        // 댓글 아이디
                article,            // 부모 게시글
                dto.getNickname(),  // 댓글 닉네임
                dto.getBody()       // 댓글 본문
        );

        
    }

    public void patch(CommentDto dto) {

        // 예외 발생
        if (!Objects.equals(this.id, dto.getId()))
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다.");

        // 객체 갱신
        if (dto.getNickname() != null)
            this.nickname = dto.getNickname();
        if (dto.getBody() != null)
            this.body = dto.getBody();

    }
}
