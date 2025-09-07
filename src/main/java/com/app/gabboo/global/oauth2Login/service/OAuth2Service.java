package com.app.gabboo.global.oauth2Login.service;


import com.app.gabboo.global.jwt.utils.JwtUtils;

import com.app.gabboo.global.oauth2Login.OAuth2UserInfo;
import com.app.gabboo.users.domain.User;
import com.app.gabboo.users.service.UserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final UserFactory userFactory;
    private final JwtUtils jwtUtils;

    public String socialLogin(OAuth2UserInfo userInfo, String provider) {
        User user = userFactory.createUserWithSocial(userInfo, provider);

        return jwtUtils.generateToken(
                user.getEmail(),
                Collections.singletonList("USER") // 기본 ROLE_USER
        );
    }
}
