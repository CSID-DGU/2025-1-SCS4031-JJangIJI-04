package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

class SimpleExpenseResponseStatusTest {

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
        Expense expense = new Expense(expenseSavingGoal, restaurant, "은화수식당", "돈가스", 8500, "냠냠굿", startDate, 5);
        ExpenseByDate expenseByDate = new ExpenseByDate(startDate, List.of(expense));
        ExpenseStatus status = ExpenseStatus.convert(expenseSavingGoal, expenseByDate);

        Assertions.assertThat(status).isEqualTo(ExpenseStatus.GOOD);
    }

    @DisplayName("데일리 지출 상태 변환 성공 : BAD")
    @Test
    void convertBAD() {
        Expense expense = new Expense(expenseSavingGoal, restaurant, "은화수식당", "돈가스", 11_000, "냠냠굿", startDate, 5);
        ExpenseByDate expenseByDate = new ExpenseByDate(startDate, List.of(expense));
        ExpenseStatus status = ExpenseStatus.convert(expenseSavingGoal, expenseByDate);

        Assertions.assertThat(status).isEqualTo(ExpenseStatus.BAD);
    }
}
