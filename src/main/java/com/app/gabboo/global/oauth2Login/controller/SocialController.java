package com.app.gabboo.global.oauth2Login.controller;

import com.app.gabboo.global.dto.ApiResponse;
import com.app.gabboo.global.oauth2Login.OAuth2UserInfo;
import com.app.gabboo.global.oauth2Login.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/social")
@RequiredArgsConstructor
public class SocialController {

    private final OAuth2Service oAuth2Service;

    @PostMapping("/login/{provider}")
    public ResponseEntity<ApiResponse<?>> socialLogin(
            @PathVariable String provider,
            @RequestBody OAuth2UserInfo userInfo
    ) {
        String token = oAuth2Service.socialLogin(userInfo, provider);
        return ResponseEntity.ok(ApiResponse.success(token, provider + " 로그인 성공"));
    }
}
