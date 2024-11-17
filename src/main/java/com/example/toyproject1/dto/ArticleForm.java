package com.example.toyproject1.dto;

import com.example.toyproject1.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString   // 데이터를 잘 받았는지 확인할 toString() 메서드 추가
@AllArgsConstructor // 전송받은 제목과 내용을 필드에 저장하는 생성자 추가
public class ArticleForm {

    private Long id;
    private String title;       // 제목을 받을 필드
    private String content;     // 내용을 받을 필드

    public Article toEntity() {
        return new Article(id, title, content);
    }

}
