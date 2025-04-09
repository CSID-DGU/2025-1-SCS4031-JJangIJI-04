package com.jjangiji.hankkimoa.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    // 전체
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 서버에러가 발생했습니다"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 인자입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
