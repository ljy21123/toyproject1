package com.example.toyproject1.service;

import com.example.toyproject1.dto.ArticleForm;
import com.example.toyproject1.entity.Article;
import com.example.toyproject1.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service    // 서비스 객체 선언
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;    // 게시글 리파지터리 객체 주입

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        
        Article article = dto.toEntity();
        
        // 이미 데이터가 있으면 null 반환
        if (article.getId() != null) {
            return null;
        }
        
        return articleRepository.save(article);
    
    }

    public Article update(Long id, ArticleForm dto) {

        // DTO를 엔티티로 반환
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        // 기존 데이터 조회
        Article existArticle = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if (existArticle == null || !id.equals(article.getId())) {
            // 400 에러 응답
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null;
        }

        // 업데이트 및 정상 응답(200)하기
        existArticle.patch(article); // 일부만 수정했을 때 수정 안 한 데이터가 null 값이 안 되도록 기존 데이터에 새 데이터를 덮어 씌우기
        return articleRepository.save(existArticle); // 최종 저장 후 반환

    }

    public Article delete(Long id) {

        // 대상 찾기
        Article inquiryArticle = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if (inquiryArticle == null) {
            return null;
        }

        // 대상 삭제
        articleRepository.delete(inquiryArticle);
        return inquiryArticle;

    }

    // @Transactional: 메서드를 하나의 트랜잭션으로 묶는다.
    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {

        // dto 묶음을 엔티티 묶음으로 변환하기           // 스트림 문법은 리스트와 같은 자료구조에 저장된 요소를 하나씩 순회하면서 처리하는 코드 패턴이다.
        List<Article> articleList = dtos.stream()   // 1. dtos를 스트림화 한다.
                .map(ArticleForm::toEntity)         // 2. map()으로 dto가 하나하나 올 때마다 dto.toEntity()를 수행해 매핑한다.
                .toList();                          // 3. 매핑한 것을 리스트로 묶는다.

        // 엔티티 묶음을 DB에 저장하기
        articleRepository.saveAll(articleList);

        // 강제 예외 발생시키기
        articleRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("결제 실패!"));

        // 결과 값 반환하기
        return articleList;

    }
}
