package com.jjangiji.hankkimoa.expense.repository;

import com.jjangiji.hankkimoa.config.RepositoryTest;
import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
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

class ExpenseSavingGoalRepositoryTest extends RepositoryTest {

    @Autowired
    private ExpenseSavingGoalRepository expenseSavingGoalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    private User user;
    private User user2;
    private final LocalDate now = LocalDate.now();

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User("hankkimoa@gmail.com", "한끼", "hankkiImage", LoginType.KAKAO, Role.USER));
        user2 = userRepository.save(new User("hankkimoa2@gmail.com", "한끼2", "hankkiImage", LoginType.KAKAO, Role.USER));
    }

    @DisplayName("최신 목표 금액 조회")
    @Test
    void findLastByUser() {
        // given
        ExpenseSavingGoal expenseSavingGoal1 = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(user,100_000,
                        LocalDate.of(2025, 4, 20),
                        LocalDate.of(2025, 4, 26)));
        ExpenseSavingGoal expenseSavingGoal2 = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(user,100_000,
                        LocalDate.of(2025, 4, 27),
                        LocalDate.of(2025, 5, 3)));

        expenseSavingGoalRepository.save(expenseSavingGoal1);
        expenseSavingGoalRepository.save(expenseSavingGoal2);

        // when
        ExpenseSavingGoal result = expenseSavingGoalRepository
                .findLastByUser(user.getId())
                .get();

        // then
        Assertions.assertThat(result).isEqualTo(expenseSavingGoal2);
    }

    @DisplayName("절약 목표 금액 조회 : 날짜가 주어진 경우")
    @Test
    void findByUserAndDate() {
        // given
        ExpenseSavingGoal expenseSavingGoal = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(user,100_000, now, now.plusDays(7)));

        // when
        ExpenseSavingGoal result = expenseSavingGoalRepository
                .findByUserAndDate(user.getId(), now.plusDays(1))
                .get();

        // then
        Assertions.assertThat(result).isEqualTo(expenseSavingGoal);
    }

    @DisplayName("유저별 최신 절약 목표 금액 조회")
    @Test
    void findAllLastExpenseSavingGoal() {
        // given
        ExpenseSavingGoal expenseSavingGoal1 = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(user,100_000, now, now.plusDays(7)));
        expenseRepository.save(new Expense(expenseSavingGoal1, null,
                "한끼식당", "순두부", 5_000,
                "든든하게 먹음!", now, 3));

        ExpenseSavingGoal expenseSavingGoal2 = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(user2,100_000, now, now.plusDays(7)));
        expenseRepository.save(new Expense(expenseSavingGoal2, null,
                "필동면옥", "냉면", 8_000,
                "든든하게 먹음!", now, 5));

        // when
        List<ExpenseSavingGoal> result = expenseSavingGoalRepository.findAllLastExpenseSavingGoalOrderByCreatedAtDESC(2, 0);

        // then
        Assertions.assertThat(result).containsExactly(expenseSavingGoal2, expenseSavingGoal1);
    }

    @DisplayName("유저별 최신 절약 목표 금액 조회 : 지출이 없는 경우")
    @Test
    void findAllLastExpenseSavingGoal_whenExpenseNotExist() {
        // given
        ExpenseSavingGoal expenseSavingGoal1 = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(user,100_000, now, now.plusDays(7)));
        expenseRepository.save(new Expense(expenseSavingGoal1, null,
                "한끼식당", "순두부", 5_000,
                "든든하게 먹음!", now, 3));

        ExpenseSavingGoal expenseSavingGoal2 = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(user2,100_000, now, now.plusDays(7)));

        // when
        List<ExpenseSavingGoal> result = expenseSavingGoalRepository.findAllLastExpenseSavingGoalOrderByCreatedAtDESC(2, 0);

        // then
        Assertions.assertThat(result).containsExactly(expenseSavingGoal1);
    }
}
