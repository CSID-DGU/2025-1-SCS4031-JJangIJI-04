package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import lombok.Getter;
import java.time.LocalDate;
import java.util.Objects;

@Getter
public class ExpenseDate {

    private final LocalDate date;

    public ExpenseDate(LocalDate date) {
        if (date == null) {
            throw new HankkiMoaException(ExceptionCode.EXPENSE_DATE_EMPTY);
        }
        if (date.isAfter(LocalDate.now())) {
            throw new HankkiMoaException(ExceptionCode.EXPENSE_DATE_INVALID);
        }

        // todo ExpenseSavingGoal 내의 지출 검증
        this.date = date;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ExpenseDate that = (ExpenseDate) object;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }
}
