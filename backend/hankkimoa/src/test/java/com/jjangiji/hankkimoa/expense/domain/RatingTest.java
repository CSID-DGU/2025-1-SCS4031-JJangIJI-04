package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RatingTest {

    @DisplayName("평점 생성 실패 : 평점이 0점 미만인 경우")
    @Test
    void failWhenRatingIsBelowMinimum() {
        // given
        int invalidRating = -1;

        // when & then
        assertThatThrownBy(() -> new Rating(invalidRating))
                .isInstanceOf(HankkiMoaException.class)
                .hasMessage(ExceptionCode.RATING_INVALID_FORMAT.getMessage());
    }

    @DisplayName("평점 생성 실패 : 평점이 5점 초과인 경우")
    @Test
    void failWhenRatingIsAboveMaximum() {
        // given
        int invalidRating = 6;

        // when & then
        assertThatThrownBy(() -> new Rating(invalidRating))
                .isInstanceOf(HankkiMoaException.class)
                .hasMessage(ExceptionCode.RATING_INVALID_FORMAT.getMessage());
    }
}
