package com.kyung.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class) // 스프링 부트 테스트와 JUnit 사이에 연결자 역활
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired  // 스프링이 관리하는 빈을 주입 받는다.
    private MockMvc mvc;    // 웹API를 테스트할 때 사용, 스프링MVC 테스트의 시작점

    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello"))          // MockMvc 를 통해 /hello 주소로 HTTP GET 요철을 한다.
                .andExpect(status().isOk())             // mvc.perform 결과를 검증, 200인지 아닌지 검증
                .andExpect(content().string(hello));    // mvc.perform 결과를 검증, 응답 본문의 내용을 검증
    }
}

/*
*   @WebMvcTest
*       여러 스프링 테스트 어노테이션 중, Web(Soring MVC)에 집중할 수 있는 어노테이션
*       선언할 경우 @Controller, @ControllerAdvice 등을 사용할 수 있다.
*       (@Service, @Component, @Repository 등은 사용할 수 없다.)
*
*    private MockMvc mvc;
*       이 클래스를 통해 HTTP GET, POST 등에 대한 API테스트를 할 수 있다.
*
*    andExpect(status().isOk())
*       HTTP Header의 Status 를 검증
*       흔히 알고 있는 200, 404, 500 등의 상태 검증
*
*    andExpect(content().string(hello))
*       Controller 에서 "hello" 를 리턴하기 떄문에 이 값이 맞는지 검증
*
* */