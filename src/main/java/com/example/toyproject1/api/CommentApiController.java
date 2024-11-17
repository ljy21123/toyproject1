package com.example.toyproject1.api;

import com.example.toyproject1.dto.CommentDto;
import com.example.toyproject1.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    /**
     * 메서드의 반환값으로 엔티티를 받는 것과 DTO로 받는 것의 차이점:
     *  엔티티 반환 방식은 간단한 API나 별다른 정보 노출 위험이 없는 상황에서 빠르고 효율적인 방식입니다. 개발 시간이 적게 들고 간결합니다.
     *  DTO 반환 방식은 더 안전하고 유지보수가 용이한 방식입니다. 특히 확장 가능성(예: 다른 형식으로 데이터 제공)이 요구되거나, 엔티티를 직접 노출하는 것이 부적절한 경우에 많이 사용됩니다.
    */

    // 댓글 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {

        // 서비스에 위임
        List<CommentDto> dtos = commentService.comments(articleId);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);

    }

    // 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) {

        // 서비스에 위임
        CommentDto createdDto = commentService.create(articleId, dto);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);

    }

    // 댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto) {

        // 서비스에 위임
        CommentDto updatedDto = commentService.update(id, dto);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);

    }

    // 댓글 삭제

    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id) {

        // 서비스에 위임
        CommentDto deletedDto = commentService.delete(id);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);

    }

}
