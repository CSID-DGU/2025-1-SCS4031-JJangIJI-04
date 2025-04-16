package com.jjangiji.hankkimoa.expense.service;

import com.jjangiji.hankkimoa.IntegrationTest;
import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import com.jjangiji.hankkimoa.expense.repository.ExpenseRepository;
import com.jjangiji.hankkimoa.expense.repository.ExpenseSavingGoalRepository;
import com.jjangiji.hankkimoa.expense.service.dto.ExpenseCreateRequest;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.Optional;

class ExpenseServiceTest extends IntegrationTest {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private ExpenseSavingGoalRepository expenseSavingGoalRepository;

    @AfterEach
    void tearDown() {
        expenseRepository.deleteAllInBatch();
        restaurantRepository.deleteAllInBatch();
        expenseSavingGoalRepository.deleteAllInBatch();
    }

    @DisplayName("지출 내역 생성 성공")
    @Test
    void createExpense() {
        // given
        Restaurant restaurantrestaurant = restaurantRepository.save(new Restaurant("한끼식당"));
        ExpenseSavingGoal expenseSavingGoal = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(80_000, LocalDate.now(), LocalDate.now().plusDays(7)));

        ExpenseCreateRequest request = new ExpenseCreateRequest(expenseSavingGoal.getId(), restaurantrestaurant.getId(),
                "한끼식당", "순두부찌개", 8000, "든든하게 먹음!", LocalDate.now(), 5);

        // when
        Long expenseId = expenseService.createExpense(request);

        // then
        Assertions.assertThat(expenseId).isNotNull();
    }

    @DisplayName("지출 내역 생성 성공 : 식당 정보가 없는 경우")
    @Test
    void createExpenseWhenRestaurantNull() {
        ExpenseSavingGoal expenseSavingGoal = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(80_000, LocalDate.now(), LocalDate.now().plusDays(7)));

        ExpenseCreateRequest request = new ExpenseCreateRequest(expenseSavingGoal.getId(), null,
                "한끼식당", "순두부찌개", 8000, "든든하게 먹음!", LocalDate.now(), 5);

        // when
        Long expenseId = expenseService.createExpense(request);

        // then
        Assertions.assertThat(expenseId).isNotNull();
    }

    @DisplayName("지출 내역 삭제 성공")
    @Test
    void deleteExpense() {
        // given
        ExpenseSavingGoal expenseSavingGoal = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(80_000, LocalDate.now(), LocalDate.now().plusDays(7)));
        ExpenseCreateRequest request = new ExpenseCreateRequest(expenseSavingGoal.getId(), null,
                "한끼식당", "순두부찌개", 8000, "든든하게 먹음!", LocalDate.now(), 5);
        Long expenseId = expenseService.createExpense(request);

        // when
        expenseService.deleteExpense(expenseId);
        Optional<Expense> expense = expenseRepository.findById(expenseId);

        // when & then
        Assertions.assertThat(expense).isEmpty();
    }

    @DisplayName("지출 내역 삭제 성공")
    @Test
    void failWhenExpenseNotExist() {
        Assertions.assertThatCode(() -> expenseService.deleteExpense(1L))
                .isInstanceOf(HankkiMoaException.class)
                .hasMessage(ExceptionCode.EXPENSE_NOT_FOUND.getMessage());
    }
}
