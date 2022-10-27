package com.kyung.book.springboot.config.auth;

import com.kyung.book.springboot.config.auth.dto.OAuthAttributes;
import com.kyung.book.springboot.config.auth.dto.SessionUser;
import com.kyung.book.springboot.domain.User.User;
import com.kyung.book.springboot.domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

// 로그인 이후 가져온 사용자의 정보들을 기반으로 가입 및 정보 수정, 세션 저장등의 기능 지원

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // getRegistrationId : 현재 로그인 진행 중인 서비스를 구분하는 코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        /*
            getUserNameAttributeName : OAuth2 로그인 진행 시 키가 되는 필드값. Primary Key 와 같은 의미
                구글의 경우엔 기본적인 코드를 지원하지만, 네이버 카카오 등은 지원 X => 구글의 기본 코드는 sub
        */
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                                            .getUserInfoEndpoint().getUserNameAttributeName();

        /*
        *   getAttributes : OAuth2UserService 를 통해 가져온 oAuth2User 의 attribute 를 담을 클래스
        * */
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user)); // SessionUser : 세션에 사용자 정보를 저장하기 위한 DTO

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                                        attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    // 구글 사용자 정보가 업데이트 되었을 때 update
    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                            .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                            .orElse(attributes.toEntity());

        return userRepository.save(user);
    }

}
