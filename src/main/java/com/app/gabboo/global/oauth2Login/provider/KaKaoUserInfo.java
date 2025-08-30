package com.app.gabboo.global.oauth2Login.provider;

import com.app.gabboo.global.oauth2Login.OAuth2UserInfo;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class KaKaoUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getName() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = kakaoAccount != null? (Map<String, Object>) kakaoAccount.get("profile") : null;
        return profile != null ? (String) profile.get("nickname") : null;
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        return kakaoAccount != null ? (String) kakaoAccount.get("email") : null;
    }
}
