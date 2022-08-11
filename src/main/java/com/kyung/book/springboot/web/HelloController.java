package com.kyung.book.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}

/*
*   @RestController
*       - 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 준다.
*       - @ResponseBody 를 각 메소드마다 선언했던 것을 한번에 사용할 수 있게 해준다.
*
*    @GetMapping
*       - HTTP Method인 GET의 요청을 받을 수 있는 API를 만들어준다.
*       - 예전에 @RequsetMapping(method = RequsetMethod.GET) 으로 사용되었다.
*         이 프로젝트는 /hello로 요청이 오면 문자열 hello를 반환하는 기능을 가지게 되었다.
*/