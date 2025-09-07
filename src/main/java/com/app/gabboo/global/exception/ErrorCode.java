package com.app.gabboo.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_INPUT(400, "잘못된 요청입니다."),
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    UNAUTHORIZED(401, "권한이 없습니다."),
    INTERNAL_ERROR(500, "서버 내부 오류가 발생했습니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
