package com.jjangiji.hankkimoa.expense.repository;

import com.jjangiji.hankkimoa.config.RepositoryTest;
import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;

class ExpenseRepositoryTest extends RepositoryTest {

    private ExpenseSavingGoal expenseSavingGoal;
    private Restaurant restaurant;

    @Autowired
    private ExpenseSavingGoalRepository expenseSavingGoalRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    @BeforeEach
    void setUp() {
        expenseSavingGoal = expenseSavingGoalRepository.save(new ExpenseSavingGoal(80_000, LocalDate.now(), LocalDate.now().plusDays(7)));
        restaurant  = restaurantRepository.save(new Restaurant("한끼식당"));
    }

    @DisplayName("오늘 지출 목록 조회 성공")
    @Test
    void findAllByExpenseDate() {
        // given
        LocalDate date = LocalDate.of(2025, 4, 17);
        LocalDate datebefore = date.minusDays(1);
        Expense expense1 = new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", date, 5);
        Expense expense2 = new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", datebefore, 5);
        expenseRepository.saveAll(List.of(expense1, expense2));

        // when
        List<Expense> expenses = expenseRepository.findAllByExpenseDateOrderByCreatedAtDesc(date);

        // then
        Assertions.assertThat(expenses).containsOnly(expense1);
    }

    @DisplayName("오늘 지출 목록 조회 성공 : 최신순 정렬")
    @Test
    void findAllByExpenseDateOrderByCreatedAtDesc() {
        // given
        LocalDate date = LocalDate.of(2025, 4, 17);
        Expense expense1 = new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", date, 5);
        expenseRepository.save(expense1);
        Expense expense2 = new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", date, 5);
        expenseRepository.save(expense2);

        // when
        List<Expense> expenses = expenseRepository.findAllByExpenseDateOrderByCreatedAtDesc(date);

        // then
        Assertions.assertThat(expenses.get(0).getCreatedAt())
                .isAfter(expenses.get(1).getCreatedAt());
    }
}
