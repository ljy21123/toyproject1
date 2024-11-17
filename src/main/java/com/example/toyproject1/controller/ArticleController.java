package com.example.toyproject1.controller;

import com.example.toyproject1.dto.ArticleForm;
import com.example.toyproject1.dto.CommentDto;
import com.example.toyproject1.entity.Article;
import com.example.toyproject1.repository.ArticleRepository;
import com.example.toyproject1.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j  // 로깅 기능을 위한 어노테이션 추가
@Controller
public class ArticleController {

    @Autowired  // 스프링 부트가 미리 생성해 놓은 리파지터리 객체 주입(의존성 주입)
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "/articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {

        log.info(form.toString());

        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) { // 매개변수로 id 받아오기

        log.info("id = " + id); // id를 잘 받았는지 확인하는 로그 찍기

        // 1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);    // 데이터 없으면 null 반환
        List<CommentDto> commentDtos = commentService.comments(id);

        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);

        // 3. 뷰 페이지 반환하기
        return "articles/show";

    }

    @GetMapping("/articles")
    public String index(Model model) {

        // 1. 모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();

        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰 페이지 설정하기
        return "articles/index";

    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){

        // 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        // 뷰 페이지 설정하기
        return "articles/edit";

    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){     // 매개변수로 DTO 받아 오기

        log.info(form.toString());

        // DTO를 엔티티로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // DB에서 기존 데이터 가져와서 확인
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 기존 데이터가 있을 시 값 갱신
        if (target != null){
            articleRepository.save(articleEntity);  // 새 엔티티를 DB에 저장
        }

        // 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();

    }

    /**
     * RedirectAttributes: 리다이렉트 페이지에서 사용할 일회성 데이터를 관리하는 객체입니다.
     * 객체명.addFlashattribute(넘겨 주려는 키 문자열, 넘겨 주려는 값 객체): 리다이렉트 시점에 한 번만 사용할 데이터를 등록합니다.
     * */
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){

        log.info("삭제 요청이 들어왔습니다!!");

        // 삭제할 대상 가져오기
        Article articleData = articleRepository.findById(id).orElse(null);
        log.info(Objects.requireNonNull(articleData).toString());   // null이 아닌 경우 로깅, null이면 NullPointerException 발생

        // 대상 엔티티 삭제
        articleRepository.delete(articleData);
        rttr.addFlashAttribute("msg", "삭제됐습니다!");

        return "redirect:/articles";

    }

}
