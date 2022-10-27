package com.kyung.book.springboot.config.auth;

import com.kyung.book.springboot.domain.User.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  // Spring Security 설정들을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    protected void configure(HttpSecurity http) throws Exception{
        http
            .csrf().disable().headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해당 옵션들을 disable
            .and()
                .authorizeRequests() // URL별 권한 관리 설정하는 옵션의 시작, authorizeRequests 가 선언되야 antMatchers 사용 가능
                .antMatchers("/", ".css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()   // anyRequest : 설정된 값들 이외 나머지 URL
            .and()
                .logout()                       // 로그아웃 기능에 대한 여러 설정의 진입점
                    .logoutSuccessUrl("/")      // 로그아웃 성공시 / 주소로 이동
            .and()
                .oauth2Login()                  // oauth2 로그인 기능에 대한 여러 설정의 진입점
                    .userInfoEndpoint()         // 로그인 성공 이후 사용자 정보를 가져올 떄 설정들을 담당
                        .userService(customOAuth2UserService);
                        // 소셜 로그인 성공 시 후속 조치를 진행할 userService 인터페이스의 구현체 등록

    }
}

/*  antMatchers
*       권한 관리 대상을 지정하는 옵션
*       URL, http 메소드별로 관리 가능
*
*   authenticated()
*       나머지 URL 은 모두 인증된 사용자(로그인한 사용자)들에게만 허용
*
* */