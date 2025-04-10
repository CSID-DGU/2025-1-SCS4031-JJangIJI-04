package com.jjangiji.hankkimoa.common.exception;

public record ExceptionResponse(String httpMethod, String path, String exceptionCode, String message) {
}
