package com.example.toyproject1.dto;

import com.example.toyproject1.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDto {

    private Long id;            // 댓글의 id
    private Long articleId;     // 댓글의 부모 id
    private String nickname;    // 댓글 작성자
    private String body;        // 댓글 본문

    // 객체 생성 없이 호출 가능 (static)
    public static CommentDto createCommentDto(Comment c) { 
        return new CommentDto(c.getId(), c.getArticle().getId(), c.getNickname(), c.getBody());
    }

}
