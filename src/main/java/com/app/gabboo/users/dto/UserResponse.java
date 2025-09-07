package com.app.gabboo.users.dto;

import com.app.gabboo.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private Long Id;
    private String email;
    private String name;

    public static UserResponse fromEntity(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getName());
    }
}
