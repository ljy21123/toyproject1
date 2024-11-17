/*
* REST 컨트롤러와 일반 컨트롤러의 차이점:
* REST 컨트롤러는 JSON이나 텍스트 같은 데이터를 반환하는 반면 일반 컨트롤러는 뷰 페이지를 반환한다.
* */

package com.example.toyproject1.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // REST API용 컨트롤러
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello World!";
    }

}
