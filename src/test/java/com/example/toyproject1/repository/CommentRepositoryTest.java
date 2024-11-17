package com.example.toyproject1.repository;

import com.example.toyproject1.entity.Article;
import com.example.toyproject1.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest    // 해당 클래스를 JPA와 연동하여 테스트
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
        // 테스트 이름을 붙일 때 사용
    void findByArticleId() {

        /* Case 1: 4번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 4L;

            // 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상 데이터
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ");   // 부모 객체 생성

            Comment a = new Comment(1L, article, "Park", "굿 윌 헌팅");
            Comment b = new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment c = new Comment(3L, article, "Choi", "쇼생크 탈출");

            List<Comment> expected = Arrays.asList(a, b, c);    // 객체 합치기

            // 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력!");
        }

        /* Case 2: 1번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 1L;

            // 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상 데이터
            Article article = new Article(1L, "가가가가", "1111");
            List<Comment> expected = List.of(); // == Arrays.asList()

            // 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "1번 글은 댓글이 없음");
        }

        /* Case 3: 9번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 9L;

            // 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상 데이터
            Article article = null;
            List<Comment> expected = List.of();

            // 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "9번 글 자체가 없으므로 댓글은 비어 있어야 함");
        }

        /* Case 4: 999번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 999L;

            // 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상 데이터
            Article article = null;
            List<Comment> expected = List.of();

            // 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "999번 글 자체가 없으므로, 댓글은 비어 있어야 함");
        }

        /* Case 5: -1번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = -1L;

            // 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상 데이터
            Article article = null;
            List<Comment> expected = List.of();

            // 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "-1번 글 자체가 없으므로, 댓글은 비어 있어야 함");
        }

    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
        // 테스트 이름을 붙일 때 사용
    void findByNickname() {

        /* Case 1: "Park"의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "Park";

            // 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상 데이터
            Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), nickname, "굿 윌 헌팅");
            Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), nickname, "치킨");
            Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), nickname, "조깅");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력!");
        }

        /* Case 3: null의 모든 댓글 조회(특정 닉네임의 입력값이 null일 때) */
        {
            // 입력 데이터 준비
            String nickname = null;

            // 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상 데이터
            List<Comment> expected = List.of();

            // 비교 및 검증
            assertEquals(expected.toString(), comments.toString(),
                    "null의 모든 댓글을 출력!");
        }

        /* Case 4: ""의 모든 댓글 조회(특정 닉네임의 입력값이 없을 때) */
        {
            // 입력 데이터 준비
            String nickname = "";

            // 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상 데이터
            List<Comment> expected = List.of();

            // 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "\"\"의 모든 댓글을 출력!");
        }

    }
}