package com.jjangiji.hankkimoa.expense.service;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.config.IntegrationTest;
import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import com.jjangiji.hankkimoa.expense.repository.ExpenseRepository;
import com.jjangiji.hankkimoa.expense.repository.ExpenseSavingGoalRepository;
import com.jjangiji.hankkimoa.expense.service.dto.DailyExpenseResponse;
import com.jjangiji.hankkimoa.expense.service.dto.ExpenseCreateRequest;
import com.jjangiji.hankkimoa.expense.service.dto.MonthlyExpenseResponse;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;
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

    private final LocalDate now = LocalDate.now();
    private final LocalDate sevenDayAfter = now.plusDays(6);

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
                new ExpenseSavingGoal(70_000, now, sevenDayAfter));

        ExpenseCreateRequest request = new ExpenseCreateRequest(expenseSavingGoal.getId(), restaurantrestaurant.getId(),
                "한끼식당", "순두부찌개", 7000, "든든하게 먹음!", LocalDate.now(), 5);

        // when
        Long expenseId = expenseService.createExpense(request);

        // then
        Assertions.assertThat(expenseId).isNotNull();
    }

    @DisplayName("지출 내역 생성 성공 : 식당 정보가 없는 경우")
    @Test
    void createExpenseWhenRestaurantNull() {
        ExpenseSavingGoal expenseSavingGoal = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(80_000, now, sevenDayAfter));

        ExpenseCreateRequest request = new ExpenseCreateRequest(expenseSavingGoal.getId(), null,
                "한끼식당", "순두부찌개", 8000, "든든하게 먹음!", LocalDate.now(), 5);

        // when
        Long expenseId = expenseService.createExpense(request);

        // then
        Assertions.assertThat(expenseId).isNotNull();
    }

    @DisplayName("데일리 지출 내역 조회 성공")
    @Test
    void readDailyExpenses() {
        // given
        ExpenseSavingGoal expenseSavingGoal = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(70_000, now, sevenDayAfter));
        Expense expense1 = new Expense(expenseSavingGoal, null, "학식", "라면", 5_000, "오늘은 대충 떼워야지", now.minusDays(1), 4);
        Expense expense2 = new Expense(expenseSavingGoal, null, "닭한마리", "닭한마리", 10_000, "오랜만에 닭한마리", now, 5);
        expenseSavingGoalRepository.save(expenseSavingGoal);
        expenseRepository.saveAll(List.of(expense1, expense2));

        // when
        DailyExpenseResponse dailyExpenseResponse = expenseService.readDailyExpenses(1L, expenseSavingGoal.getId(), now);

        // then
        Assertions.assertThat(dailyExpenseResponse.dailyExpensesStatus()).hasSize(2);
        Assertions.assertThat(dailyExpenseResponse.savingGoalStatus().budget()).isEqualTo(70_000);
        Assertions.assertThat(dailyExpenseResponse.expenses()).hasSize(1);
    }

    @DisplayName("데일리 지출 내역 조회 성공 : 지출이 존재하지 않는 경우")
    @Test
    void readDailyExpenses_withNoExpenses() {
        // given
        ExpenseSavingGoal expenseSavingGoal = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(70_000, now, sevenDayAfter));
        expenseSavingGoalRepository.save(expenseSavingGoal);

        // when
        DailyExpenseResponse dailyExpenseResponse = expenseService.readDailyExpenses(1L, expenseSavingGoal.getId(), now);

        // then
        Assertions.assertThat(dailyExpenseResponse.dailyExpensesStatus()).isEmpty();
        Assertions.assertThat(dailyExpenseResponse.savingGoalStatus().budget()).isEqualTo(70_000);
        Assertions.assertThat(dailyExpenseResponse.expenses()).isEmpty();
    }

    @DisplayName("지출 한달 내역 조회 성공")
    @Test
    void readMonthlyExpenses() {
        // given
        LocalDate sevenBefore = now.minusDays(7);
        ExpenseSavingGoal expenseSavingGoal1 = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(140_000, sevenBefore, now));
        ExpenseSavingGoal expenseSavingGoal2 = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(70_000, now, sevenDayAfter));
        Expense expense1 = new Expense(expenseSavingGoal1, null, "산타돈부리", "사케동", 13_000, "사케동 맛있다 ~", sevenBefore, 5);
        Expense expense2 = new Expense(expenseSavingGoal2, null, "닭한마리", "닭한마리", 12_000, "오랜만에 닭한마리", now, 5);
        expenseSavingGoalRepository.saveAll(List.of(expenseSavingGoal1, expenseSavingGoal2));
        expenseRepository.saveAll(List.of(expense1, expense2));

        // when
        MonthlyExpenseResponse monthlyExpenseResponse = expenseService.readMonthlyExpenses(1L, sevenBefore, now);

        // then
        Assertions.assertThat(monthlyExpenseResponse.dailyExpenseStatus()).hasSize(2);
        Assertions.assertThat(monthlyExpenseResponse.dailyExpenseOverBudgetCount()).isEqualTo(1);
        Assertions.assertThat(monthlyExpenseResponse.monthlyExpenseRecordCount()).isEqualTo(2);
    }

    @DisplayName("지출 내역 삭제 성공")
    @Test
    void deleteExpense() {
        // given
        ExpenseSavingGoal expenseSavingGoal = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(80_000, now, sevenDayAfter));
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
