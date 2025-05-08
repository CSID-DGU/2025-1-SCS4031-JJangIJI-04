package com.jjangiji.hankkimoa.expense.repository;

import com.jjangiji.hankkimoa.config.RepositoryTest;
import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import com.jjangiji.hankkimoa.user.domain.LoginType;
import com.jjangiji.hankkimoa.user.domain.Role;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;

class ExpenseRepositoryTest extends RepositoryTest {

    private User user;
    private ExpenseSavingGoal expenseSavingGoal;
    private ExpenseSavingGoal expenseSavingGoal2;
    private Restaurant restaurant;
    private final LocalDate now = LocalDate.now();
    private final LocalDate before = now.minusDays(1);

    @Autowired
    private ExpenseSavingGoalRepository expenseSavingGoalRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User("hankkimoa@gmail.com", "한끼", "hankkiImage", LoginType.KAKAO, Role.USER));
        expenseSavingGoal = expenseSavingGoalRepository.save(new ExpenseSavingGoal(user, 80_000, LocalDate.now(), LocalDate.now().plusDays(7)));
        expenseSavingGoal2 = expenseSavingGoalRepository.save(new ExpenseSavingGoal(user, 100_000, LocalDate.now(), LocalDate.now().plusDays(7)));
        restaurant  = restaurantRepository.save(new Restaurant("한끼식당"));
    }

    @DisplayName("오늘 지출 목록 조회 성공")
    @Test
    void findAllByExpenseDate() {
        // given
        Expense expense1 = new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", now, 5);
        Expense expense2 = new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", before, 5);
        expenseRepository.saveAll(List.of(expense1, expense2));

        // when
        List<Expense> expenses = expenseRepository.findAllByExpenseDateOrderByCreatedAtDesc(now);

        // then
        Assertions.assertThat(expenses).containsOnly(expense1);
    }

    @DisplayName("오늘 지출 목록 조회 성공 : 최신순 정렬")
    @Test
    void findAllByExpenseDateOrderByCreatedAtDesc() {
        // given
        Expense expense1 = new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", now, 5);
        expenseRepository.save(expense1);
        Expense expense2 = new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", now, 5);
        expenseRepository.save(expense2);

        // when
        List<Expense> expenses = expenseRepository.findAllByExpenseDateOrderByCreatedAtDesc(now);

        // then
        Assertions.assertThat(expenses.get(0).getCreatedAt())
                .isAfter(expenses.get(1).getCreatedAt());
    }

    @DisplayName("지출 절약 목표 금액 내의 지출 전부 조회")
    @Test
    void findAllByExpenseSavingGoalOrder() {
        // given
        Expense expense1 = new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", now, 5);
        Expense expense2 = new Expense(expenseSavingGoal2, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", now, 5);
        expenseRepository.saveAll(List.of(expense1, expense2));

        // when
        List<Expense> result = expenseRepository.findAllByExpenseSavingGoalOrderByExpenseDateAsc(expenseSavingGoal);

        // then
        Assertions.assertThat(result).containsOnly(expense1);
    }

    @DisplayName("지출 절약 목표 금액 내의 지출 전부 조회 : 오름차순")
    @Test
    void findAllByExpenseSavingGoalOrderByExpenseDateAsc() {
        // given
        Expense expense1 = new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", before, 5);
        Expense expense2 = new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", now, 5);
        expenseRepository.saveAll(List.of(expense1, expense2));

        // when
        List<Expense> result = expenseRepository.findAllByExpenseSavingGoalOrderByExpenseDateAsc(expenseSavingGoal);

        // then
        Assertions.assertThat(result).containsExactly(expense1, expense2);
    }

    @DisplayName("지출 한달 내역 조회 성공")
    @Test
    void findAllByExpenseDateBetweenOrderByExpenseDateAsc() {
        // given
        Expense expense1 = new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", before, 5);
        Expense expense2 = new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", now, 5);
        expenseRepository.saveAll(List.of(expense1, expense2));

        // when
        List<Expense> results = expenseRepository.findAllByExpenseDateBetweenOrderByExpenseDateAsc(before, now);

        // then
        Assertions.assertThat(results).containsExactly(expense1, expense2);
    }

    @DisplayName("지출 한달 내역 조회 성공 : 주어진 날짜 범위 벗어난 경우")
    @Test
    void findAllByExpenseDateBetweenOrderByExpenseDateAsc_withOutOfRange() {
        // given
        Expense expense1 = new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", before.minusDays(1), 5);
        Expense expense2 = new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", now, 5);
        expenseRepository.saveAll(List.of(expense1, expense2));

        // when
        List<Expense> results = expenseRepository.findAllByExpenseDateBetweenOrderByExpenseDateAsc(before, now);

        // then
        Assertions.assertThat(results).containsExactly(expense2);
    }
}
