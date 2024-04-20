package com.nam4o.myweb.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 회원
    MEMBER_NOT_EXIST(HttpStatus.BAD_REQUEST, "M-001", "존재하지 않는 회원입니다."),
    EMAIL_EXIST(HttpStatus.BAD_REQUEST, "M-002", "이미 존재하는 이메일입니다."),

    // Validation
    NOT_VALID_REQUEST(HttpStatus.BAD_REQUEST, "I-001", "요청변수가 유효하지 않습니다.");

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
