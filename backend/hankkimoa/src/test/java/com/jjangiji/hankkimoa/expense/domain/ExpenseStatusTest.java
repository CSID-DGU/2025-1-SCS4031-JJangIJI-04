package com.jjangiji.hankkimoa.expense.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExpenseStatusTest {

    @DisplayName("지출 상태 메시지 변환 성공 : FAIL")
    @Test
    void convertSUCCESS() {
        // given
        int budget = 80_000;
        int expense = 20_000;

        // when
        ExpenseStatus message = ExpenseStatus.convert(budget, expense);

        // then
        Assertions.assertThat(message).isEqualTo(ExpenseStatus.SUCCESS);
    }

    @DisplayName("지출 상태 메시지 변환 성공 : SUCCESS")
    @Test
    void convertFAIL() {
        // given
        int budget = 80_000;
        int expense = 80_000;

        // when
        ExpenseStatus message = ExpenseStatus.convert(budget, expense);

        // then
        Assertions.assertThat(message).isEqualTo(ExpenseStatus.FAIL);
    }
}
