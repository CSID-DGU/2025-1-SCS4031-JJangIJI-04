package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExpenseDateTest {

    @DisplayName("지출 내역 생성 실패 : 지출일 존재하지 않을 경우")
    @Test
    void failWhenExpenseDateIsNull() {
        // given
        LocalDate invalidDate = null;

        // when & then
        assertThatThrownBy(() -> new ExpenseDate(invalidDate))
                .isInstanceOf(HankkiMoaException.class)
                .hasMessage(ExceptionCode.EXPENSE_DATE_EMPTY.getMessage());
    }

    @DisplayName("지출 내역 생성 실패 : 지출일이 미래의 날짜인 경우")
    @Test
    void failWhenExpenseDateIsInFuture() {
        // given
        LocalDate futureDate = LocalDate.now().plusDays(1);

        // when & then
        assertThatThrownBy(() -> new ExpenseDate(futureDate))
                .isInstanceOf(HankkiMoaException.class)
                .hasMessage(ExceptionCode.EXPENSE_DATE_INVALID.getMessage());
    }
}
