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

    // 지출
    EXPENSE_NOT_FOUND(HttpStatus.BAD_REQUEST, "지출이 존재하지 않습니다."),
    EXPENSE_DATE_NOT_SAME(HttpStatus.INTERNAL_SERVER_ERROR, "지출일이 일치하지 않습니다."),
    EXPENSE_DATE_INVALID(HttpStatus.BAD_REQUEST, "지출일은 미래일 수 없습니다."),
    RATING_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "평점은 0 ~ 5점 사이값이어야 합니다."),
    MEMO_INVALID_LENGTH(HttpStatus.BAD_REQUEST, "메모는 100자를 초과할 수 없습니다."),
    EMOJI_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이모지가 이미 존재합니다."),
    EMOJI_NOT_FOUND(HttpStatus.BAD_REQUEST, "이모지가 존재하지 않습니다"),

    // 지출 목표 금액
    EXPENSE_SAVING_GOAL_NOT_FOUND(HttpStatus.BAD_REQUEST, "지출 목표 금액이 존재하지 않습니다."),
    EXPENSE_SAVING_GOAL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "지출 목표 금액이 이미 존재합니다."),
    // 식당
    RESTAURANT_NOT_FOUND(HttpStatus.BAD_REQUEST, "식당이 존재하지 않습니다."),

    // 돈
    MONEY_NEGATIVE(HttpStatus.BAD_REQUEST, "금액은 음수값을 가질 수 없습니다."),

    // 날짜
    DATE_RANGE_INVALID(HttpStatus.BAD_REQUEST, "시작일이 종료일보다 앞설 수 없습니다."),

    // 유저
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "유저가 존재하지 않습니다."), ;

    private final HttpStatus httpStatus;
    private final String message;
}
