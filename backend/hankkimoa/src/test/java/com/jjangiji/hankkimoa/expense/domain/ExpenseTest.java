package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExpenseTest {

    private final ExpenseSavingGoal expenseSavingGoal = new ExpenseSavingGoal(1L, 80_000, LocalDate.now(), LocalDate.now().plusDays(7));
    private final Restaurant restaurant = new Restaurant(1L, "한끼식당");
    private final LocalDate now = LocalDate.now();

    @DisplayName("메모 생성 실패 : 글자수 100자 초과할 때")
    @Test
    void failWhenMemoExceedsMaxLength() {
        // given
        String overLengthMemo = "a".repeat(101);

        // when & then
        assertThatThrownBy(() -> new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                overLengthMemo, now, 5))
                .isInstanceOf(HankkiMoaException.class)
                .hasMessageContaining(ExceptionCode.MEMO_INVALID_LENGTH.getMessage());
    }

    @DisplayName("지출 내역 생성 실패 : 지출일이 미래의 날짜인 경우")
    @Test
    void failWhenExpenseDateIsInFuture() {
        // given
        LocalDate futureDate = LocalDate.now().plusDays(1);

        // when & then
        assertThatThrownBy(() -> new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", futureDate, 5))
                .isInstanceOf(HankkiMoaException.class)
                .hasMessage(ExceptionCode.EXPENSE_DATE_INVALID.getMessage());
    }

    @DisplayName("평점 생성 실패 : 평점이 0점 미만인 경우")
    @Test
    void failWhenRatingIsBelowMinimum() {
        // given
        int invalidRating = -1;

        // when & then
        assertThatThrownBy(() -> new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", now, invalidRating))
                .isInstanceOf(HankkiMoaException.class)
                .hasMessage(ExceptionCode.RATING_INVALID_FORMAT.getMessage());
    }

    @DisplayName("평점 생성 실패 : 평점이 5점 초과인 경우")
    @Test
    void failWhenRatingIsAboveMaximum() {
        // given
        int invalidRating = 6;

        // when & then
        assertThatThrownBy(() -> new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", now, invalidRating))
                .isInstanceOf(HankkiMoaException.class)
                .hasMessage(ExceptionCode.RATING_INVALID_FORMAT.getMessage());
    }


    @DisplayName("금액 생성 실패 : 음수인 경우")
    @Test
    void failWhenMoneyIsNegative() {
        // given
        int negativeAmount = -1;

        // when & then
        assertThatThrownBy(() -> new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", negativeAmount,
                "든든하게 먹음!", now, 5))
                .isInstanceOf(HankkiMoaException.class)
                .hasMessage(ExceptionCode.MONEY_NEGATIVE.getMessage());
    }
}
