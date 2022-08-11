package com.kyung.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*  @SpringBootApplication
*       스프링 부트의 자동 설정, 스프링 bean 읽기와 생성을 모두 자동으로 설정
*       @SpringBootApplication 이 있는 위치부터 설정을 읽기 떄문에 항상 프로젝트 상단에 위치
 * */

@SpringBootApplication
public class Application {
    public static void main(String[] args){
        // SpringApplication.run 으로 인해 내장 WAS 를 실행
        // 내장 WAS란 별도로 외부에 WAS 를 두지 않고 애플리케이션을 실행할 때 내부에서 WAS를 실행
        // 톰캣을 설치할 필요가 없게 되고, 스프링 부트로 만들어진 JAR 파일로 실행하면 된다.
        SpringApplication.run(Application.class, args);
    }
}
