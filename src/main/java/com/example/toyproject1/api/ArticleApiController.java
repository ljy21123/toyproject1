package com.example.toyproject1.api;

import com.example.toyproject1.dto.ArticleForm;
import com.example.toyproject1.entity.Article;
import com.example.toyproject1.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {

    @Autowired
    private ArticleService articleService;  // 서비스 객체 주입

    // 복수 GET(조회)
    // 뷰 페이지가 아닌 Article 엔티티를 반환하기 때문에 반환형은 Article로 한다.
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }

    // 단일 GET(조회)
    // DB에서 id로 검색해 얻은 엔티티를 가져온다.
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

    // POST(생성)
    // 생성할 데이터를 dto 매개변수로 받아 온다.
    // @RequestBody: JSON 형식으로 데이터를 전달하는 어노테이션
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {

        Article created = articleService.create(dto);

        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PATCH(수정)
    // ResponseEntity<Article>: Article을 ResponseEntity에 담아 반환하는 데이터에 상태 코드를 실어 보낸다.
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {

        Article updated = articleService.update(id, dto);

        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // DELETE(삭제)
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {

        Article deleted = articleService.delete(id);

        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // body(null) == build()
    }

    // 트랜잭션 테스트
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) {

        List<Article> createdList = articleService.createArticles(dtos);

        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

}
