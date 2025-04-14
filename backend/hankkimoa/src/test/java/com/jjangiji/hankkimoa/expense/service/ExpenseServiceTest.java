package com.jjangiji.hankkimoa.expense.service;

import com.jjangiji.hankkimoa.IntegrationTest;
import com.jjangiji.hankkimoa.expense.dto.ExpenseCreateRequest;
import com.jjangiji.hankkimoa.expense.repository.ExpenseSavingGoalEntity;
import com.jjangiji.hankkimoa.expense.repository.ExpenseSavingGoalRepository;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantEntity;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;

class ExpenseServiceTest extends IntegrationTest {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private ExpenseSavingGoalRepository expenseSavingGoalRepository;

    @DisplayName("지출 내역 생성 성공")
    @Test
    void createExpense() {
        // given
        RestaurantEntity restaurantEntity = restaurantRepository.save(new RestaurantEntity("한끼식당"));
        ExpenseSavingGoalEntity expenseSavingGoalEntity = expenseSavingGoalRepository.save(
                new ExpenseSavingGoalEntity(80_000, LocalDate.now(), LocalDate.now().plusDays(7)));

        ExpenseCreateRequest request = new ExpenseCreateRequest(expenseSavingGoalEntity.getId(), restaurantEntity.getId(),
                "한끼식당", "순두부찌개", 8000, "든든하게 먹음!", LocalDate.now(), 5);

        // when
        Long expenseId = expenseService.createExpense(request);

        // then
        Assertions.assertThat(expenseId).isNotNull();
    }

    @DisplayName("지출 내역 생성 성공 : 식당 정보가 없는 경우")
    @Test
    void createExpenseWhenRestaurantNull() {
        ExpenseSavingGoalEntity expenseSavingGoalEntity = expenseSavingGoalRepository.save(
                new ExpenseSavingGoalEntity(80_000, LocalDate.now(), LocalDate.now().plusDays(7)));

        ExpenseCreateRequest request = new ExpenseCreateRequest(expenseSavingGoalEntity.getId(), null,
                "한끼식당", "순두부찌개", 8000, "든든하게 먹음!", LocalDate.now(), 5);

        // when
        Long expenseId = expenseService.createExpense(request);

        // then
        Assertions.assertThat(expenseId).isNotNull();
    }
}
