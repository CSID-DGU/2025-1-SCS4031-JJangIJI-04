package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

class ExpensesByDateTest {

    private final LocalDate now = LocalDate.now();
    private final ExpenseSavingGoal expenseSavingGoal = new ExpenseSavingGoal(1L, 70_000, now, now.plusDays(6));
    private final Restaurant restaurant = new Restaurant(1L, "한끼식당");

    @DisplayName("지출 초과 횟수 조회 성공")
    @Test
    void getExpenseOverBudgetCount() {
        // given
        Expense expense1 = new Expense(expenseSavingGoal, restaurant, "은화수식당", "돈가스", 10_000, "냠냠굿", now.minusDays(1), 5);
        Expense expense2 = new Expense(expenseSavingGoal, restaurant, "산타돈부리", "사케동", 13_000, "사케동 맛있다 ~", now, 5);

        ExpensesByDate expensesByDate = new ExpensesByDate(List.of(expense1, expense2));

        // when
        int count = expensesByDate.getExpenseOverBudgetCount();

        // then
        Assertions.assertThat(count).isEqualTo(1);
    }
}
