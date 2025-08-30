package com.app.gabboo.global.oauth2Login.provider;


import com.app.gabboo.global.oauth2Login.OAuth2UserInfo;
import lombok.AllArgsConstructor;

import java.util.Map;
@AllArgsConstructor
public class NaverUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;

    @Override
    public String getId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return response != null ? (String) response.get("id") : null;
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return response != null ? (String) response.get("name") : null;
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return response != null ? (String) response.get("email") : null;
    }
}
