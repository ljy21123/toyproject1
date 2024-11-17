package com.example.toyproject1.service;

import com.example.toyproject1.dto.ArticleForm;
import com.example.toyproject1.entity.Article;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 해당 클래스를 스프링 부트와 연동하여 테스트
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test   // 해당 메서드가 테스트 코드임을 선언
    void index() {

        // 예상 데이터
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");

        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));    // Arrays.asList()는 입력된 배열들을 정적 리스트로 만들어 반환한다.

        // 실제 데이터
        List<Article> articles = articleService.index();

        // 비교 및 검증
        assertEquals(expected.toString(), articles.toString());

    }

    @Test
    void show_성공_존재하는_id_입력() {

        // 예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");

        // 실제 데이터
        Article article = articleService.show(id);

        // 비교 및 검증
        assertEquals(expected.toString(), article.toString());  // 두 데이터를 비교해 일치하면 테스트를 통과시킨다.

    }

    @Test
    void show_실패_존재하지_않는_id_입력() {

        // 예상 데이터
        Long id = -1L;
        Article expected = null;

        // 실제 데이터
        Article article = articleService.show(id);

        // 비교 및 검증
        assertEquals(expected, article);

    }

    @Test
    @Transactional  // 테스트가 끝나면 롤백
    void create_성공_title과_content만_있는_dto_입력() {

        // 예상 데이터
        String title = "라라라라";
        String content = "4444";

        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        // 실제 데이터
        Article article = articleService.create(dto);

        // 비교 및 검증
        assertEquals(expected.toString(), article.toString());

    }

    @Test
    @Transactional
    void create_실패_id가_포함된_dto_입력() {

        // 예상 데이터
        Long id = 4L;
        String title = "라라라라";
        String content = "4444";

        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;

        // 실제 데이터
        Article article = articleService.create(dto);

        // 비교 및 검증
        assertEquals(expected, article);

    }

    @Test
    @Transactional
    void update_성공_존재하는_id와_title_content가_있는_dto_입력() {

        // 예상 데이터
        Long id = 1L;
        String title = "가나다라";
        String content = "1234";

        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(id, title,content);

        // 실제 데이터
        Article article = articleService.update(id, dto);

        // 비교 및 검증
        assertEquals(expected.toString(), article.toString());

    }

    @Test
    @Transactional
    void update_성공_존재하는_id와_title만_있는_dto_입력() {

        // 예상 데이터
        Long id = 1L;
        String title = "AAAA";
        String content = null;

        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(1L, "AAAA" , "1111");

        // 실제 데이터
        Article article = articleService.update(id, dto);

        // 비교 및 검증
        assertEquals(expected.toString(), article.toString());

    }

    @Test
    @Transactional
    void update_실패_존재하지_않는_id의_dto_입력() {

        // 예상 데이터
        Long id = -1L;
        String title = "가나다라";
        String content = "1234";

        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;

        // 실제 데이터
        Article article = articleService.update(id, dto);

        // 비교 및 검증
        assertEquals(expected, article);

    }

    @Test
    @Transactional
    void delete_성공_존재하는_id_입력() {

        // 예상 데이터
        Long id = 1L;

        Article expected = new Article(id, "가가가가", "1111");

        // 실제 데이터
        Article article = articleService.delete(id);

        // 비교 및 검증
        assertEquals(expected.toString(), article.toString());

    }

    @Test
    @Transactional
    void delete_실패_존재하지_않는_id_입력() {

        // 예상 데이터
        Long id = -1L;

        Article expected = null;

        // 실제 데이터
        Article article = articleService.delete(id);

        // 비교 및 검증
        assertEquals(expected, article);

    }

}