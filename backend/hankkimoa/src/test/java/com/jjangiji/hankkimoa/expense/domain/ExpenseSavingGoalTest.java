package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

class ExpenseSavingGoalTest {

    private final ExpenseSavingGoal expenseSavingGoal = new ExpenseSavingGoal(1L, 80_000, LocalDate.now(), LocalDate.now().plusDays(7));
    private final Restaurant restaurant = new Restaurant(1L, "한끼식당");
    private final LocalDate now = LocalDate.now();

    @DisplayName("남은 예산 계산 성공")
    @Test
    void calculateRemainingBudget() {
        // given
        Expense expense1 = new Expense(expenseSavingGoal, restaurant, "은화수식당", "돈가스", 10_000, "냠냠굿", now, 5);
        Expense expense2 = new Expense(expenseSavingGoal, restaurant, "왕순이김밥", "김밥", 5_000, "가성비굿", now, 5);

        // when
        int remainingBudget = expenseSavingGoal.calculateRemainingBudget(List.of(expense1, expense2));

        // then
        Assertions.assertThat(remainingBudget).isEqualTo(65_000);
    }
}
