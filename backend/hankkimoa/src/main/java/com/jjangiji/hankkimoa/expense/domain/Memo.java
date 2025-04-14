package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import lombok.Getter;
import java.util.Objects;

@Getter
public class Memo {

    private static final int MAX_LENGTH = 100;

    private final String value;

    public Memo(String value) {
        if (value.length() > MAX_LENGTH) {
            throw new HankkiMoaException(ExceptionCode.MEMO_INVALID_LENGTH);
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Memo memo = (Memo) object;
        return Objects.equals(value, memo.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
