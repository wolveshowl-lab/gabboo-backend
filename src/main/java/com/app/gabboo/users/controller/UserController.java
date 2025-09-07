package com.app.gabboo.users.controller;

import com.app.gabboo.global.dto.ApiResponse;
import com.app.gabboo.global.exception.CustomException;
import com.app.gabboo.global.exception.ErrorCode;
import com.app.gabboo.users.domain.User;
import com.app.gabboo.users.dto.UserResponse;
import com.app.gabboo.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return ResponseEntity.ok(ApiResponse.success(UserResponse.fromEntity(user), "사용자 조회 성공"));
    }
}
