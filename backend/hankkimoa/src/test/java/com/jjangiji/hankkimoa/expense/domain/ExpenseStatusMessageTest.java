package com.jjangiji.hankkimoa.expense.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExpenseStatusMessageTest {

    @DisplayName("지출 상태 메시지 변환 성공 : FAIL")
    @Test
    void convertSUCCESS() {
        // given
        int budget = 80_000;
        int expense = 20_000;

        // when
        ExpenseStatusMessage message = ExpenseStatusMessage.convert(budget, expense);

        // then
        Assertions.assertThat(message).isEqualTo(ExpenseStatusMessage.SUCCESS);
    }

    @DisplayName("지출 상태 메시지 변환 성공 : SUCCESS")
    @Test
    void convertFAIL() {
        // given
        int budget = 80_000;
        int expense = 80_000;

        // when
        ExpenseStatusMessage message = ExpenseStatusMessage.convert(budget, expense);

        // then
        Assertions.assertThat(message).isEqualTo(ExpenseStatusMessage.FAIL);
    }
}
