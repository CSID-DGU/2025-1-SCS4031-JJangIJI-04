package com.jjangiji.hankkimoa.common.handler;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.ExceptionResponse;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HankkiMoaException.class)
    public ResponseEntity<ExceptionResponse> handleHankkiMoaException(HankkiMoaException exception,
                                                                      HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                request.getMethod(),
                request.getRequestURI(),
                exception.getExceptionCode(),
                exception.getMessage());

        return ResponseEntity.status(exception.getHttpStatusCode())
                .body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                request.getMethod(),
                request.getRequestURI(),
                ExceptionCode.INTERNAL_SERVER_ERROR.name(),
                ExceptionCode.INTERNAL_SERVER_ERROR.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {
        FieldError fieldError = exception.getFieldError();
        String errorMessage = ExceptionCode.INVALID_PARAMETER.getMessage();
        if (fieldError != null) {
            errorMessage = fieldError.getDefaultMessage();
        }

        ExceptionResponse response = new ExceptionResponse(
                request.getMethod(),
                request.getRequestURI(),
                ExceptionCode.INVALID_PARAMETER.name(),
                errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
