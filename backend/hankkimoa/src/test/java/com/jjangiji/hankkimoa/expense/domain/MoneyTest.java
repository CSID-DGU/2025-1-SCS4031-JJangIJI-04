package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {

    @DisplayName("금액 생성 실패 : 음수인 경우")
    @Test
    void failWhenMoneyIsNegative() {
        // given
        int negativeAmount = -1;

        // when & then
        assertThatThrownBy(() -> new Money(negativeAmount))
                .isInstanceOf(HankkiMoaException.class)
                .hasMessage(ExceptionCode.MONEY_NEGATIVE.getMessage());
    }
}
