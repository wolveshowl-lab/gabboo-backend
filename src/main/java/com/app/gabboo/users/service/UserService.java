package com.app.gabboo.users.service;

import com.app.gabboo.users.domain.User;
import com.app.gabboo.users.dto.SignupRequest;
import com.app.gabboo.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserFactory userFactory;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signUp(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일");
        }

        // 패스워드 암호화 후 Factory Builder 호출
        String encodedPwd = passwordEncoder.encode(request.getPassword());
        return userFactory.createUser(request.getEmail(), request.getName(), encodedPwd, "USER");
    }
}
