package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemoTest {

    @DisplayName("메모 생성 실패 : 글자수 100자 초과할 때")
    @Test
    void failWhenMemoExceedsMaxLength() {
        // given
        String overLengthMemo = "a".repeat(101);

        // when & then
        assertThatThrownBy(() -> new Memo(overLengthMemo))
                .isInstanceOf(HankkiMoaException.class)
                .hasMessageContaining(ExceptionCode.MEMO_INVALID_LENGTH.getMessage());
    }
}
