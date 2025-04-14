package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import lombok.Getter;
import java.util.Objects;

@Getter
public class Rating {

    private final int value;

    public Rating(int value) {
        if (value < 0 || value > 5) {
            throw new HankkiMoaException(ExceptionCode.RATING_INVALID_FORMAT);
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
        Rating rating = (Rating) object;
        return value == rating.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
