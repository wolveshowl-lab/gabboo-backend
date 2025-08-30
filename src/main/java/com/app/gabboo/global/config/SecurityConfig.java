package com.app.gabboo.global.config;

import com.app.gabboo.users.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**", "/oauth2/**").permitAll()
                .anyRequest().authenticated()
        )
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")
            )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .oauth2Login(oauth -> oauth.userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService)));
        return http.build();
    }
}
