package com.app.gabboo.users.service;

import com.app.gabboo.global.oauth2Login.OAuth2UserInfo;
import com.app.gabboo.socialAccount.domain.SocialAccount;
import com.app.gabboo.socialAccount.repository.SocialAccountRepository;
import com.app.gabboo.users.domain.User;
import com.app.gabboo.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFactory {

    private final UserRepository userRepository;
    private final SocialAccountRepository socialAccountRepository;

    public User createUserWithSocial(OAuth2UserInfo userInfo, String provider) {
        Optional<SocialAccount> socialOpt = socialAccountRepository.findByProviderAndProviderId(provider, userInfo.getId());
        if (socialOpt.isPresent()) {
            return socialOpt.get().getUser();
        }

        User user = userRepository.save(User.builder()
                .email(userInfo.getEmail())
                .name(userInfo.getName())
                .roles("USER")
                .build());

        SocialAccount social = SocialAccount.builder()
                .user(user)
                .provider(provider)
                .providerId(userInfo.getId())
                .build();
        socialAccountRepository.save(social);

        return user;
    }

    public User createUser(String email, String name, String password, String roles) {
        return userRepository.save(User.builder()
                .email(email)
                .name(name)
                .password(password)
                .roles(roles)
                .build());
    }
}
