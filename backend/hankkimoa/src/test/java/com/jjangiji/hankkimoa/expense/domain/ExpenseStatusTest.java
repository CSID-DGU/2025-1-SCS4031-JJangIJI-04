package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class ExpenseStatusTest {

    private final LocalDate startDate = LocalDate.of(2025, 4, 1);
    private final ExpenseSavingGoal expenseSavingGoal = new ExpenseSavingGoal(1L,
            100_000,
            startDate,
            LocalDate.of(2025, 4, 10));
    private final Restaurant restaurant = new Restaurant(1L, "한끼식당");

    @DisplayName("데일리 지출 상태 변환 성공 : GOOD")
    @Test
    void convertGOOD() {
        // 예산: 10000원, 기간: 10일 → 하루 예산: 10000
        ExpenseStatus status = ExpenseStatus.convert(expenseSavingGoal, 8500);

        Assertions.assertThat(status).isEqualTo(ExpenseStatus.GOOD);
    }

    @DisplayName("데일리 지출 상태 변환 성공 : BAD")
    @Test
    void convertBAD() {
        ExpenseStatus status = ExpenseStatus.convert(expenseSavingGoal, 11_000);

        Assertions.assertThat(status).isEqualTo(ExpenseStatus.BAD);
    }
}
