package com.app.gabboo.users.service;

import com.app.gabboo.global.oauth2Login.OAuth2UserInfo;
import com.app.gabboo.global.oauth2Login.provider.GoogleUserInfo;
import com.app.gabboo.global.oauth2Login.provider.KaKaoUserInfo;
import com.app.gabboo.global.oauth2Login.provider.NaverUserInfo;
import com.app.gabboo.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserFactory userFactory;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId(); // google, kakao, naver
        OAuth2UserInfo userInfo;

        switch(provider) {
            case "google": userInfo = new GoogleUserInfo(oAuth2User.getAttributes()); break;
            case "kakao":  userInfo = new KaKaoUserInfo(oAuth2User.getAttributes()); break;
            case "naver":  userInfo = new NaverUserInfo(oAuth2User.getAttributes()); break;
            default: throw new OAuth2AuthenticationException("지원하지 않는 소셜 로그인입니다.");
        }

        User user = userFactory.createUserWithSocial(userInfo, provider);
        return oAuth2User;
    }

}
