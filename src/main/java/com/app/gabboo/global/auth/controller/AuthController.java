package com.app.gabboo.global.auth.controller;

import com.app.gabboo.global.auth.dto.LoginRequest;
import com.app.gabboo.global.auth.dto.TokenResponse;
import com.app.gabboo.global.dto.ApiResponse;
import com.app.gabboo.global.exception.CustomException;
import com.app.gabboo.global.exception.ErrorCode;
import com.app.gabboo.global.jwt.utils.JwtUtils;
import com.app.gabboo.users.domain.User;
import com.app.gabboo.users.dto.SignupRequest;
import com.app.gabboo.users.service.CustomUserDetailsService;
import com.app.gabboo.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> signup(@RequestBody SignupRequest request) {
        User user = userService.signUp(request);
        return ResponseEntity.ok(ApiResponse.success(user.getEmail(), "회원가입 성공"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtils.generateToken(
                userDetails.getUsername(),
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );

        return ResponseEntity.ok(ApiResponse.success(new TokenResponse(token, "Bearer"), "로그인 성공"));
    }

}
