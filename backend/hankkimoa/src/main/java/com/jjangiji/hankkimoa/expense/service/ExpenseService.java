package com.jjangiji.hankkimoa.expense.service;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.dto.ExpenseCreateRequest;
import com.jjangiji.hankkimoa.expense.repository.ExpenseEntity;
import com.jjangiji.hankkimoa.expense.repository.ExpenseRepository;
import com.jjangiji.hankkimoa.expense.repository.ExpenseSavingGoalEntity;
import com.jjangiji.hankkimoa.expense.repository.ExpenseSavingGoalRepository;
import com.jjangiji.hankkimoa.place.repository.RestaurantEntity;
import com.jjangiji.hankkimoa.place.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ExpenseService {

    private final RestaurantRepository restaurantRepository;
    private final ExpenseSavingGoalRepository expenseSavingGoalRepository;
    private final ExpenseRepository expenseRepository;

    @Transactional
    public Long createExpense(ExpenseCreateRequest request) {
        // todo 유저 목표 금액여부 확인
        ExpenseSavingGoalEntity expenseSavingGoalEntity = getExpenseSavingGoalEntity(request.expectSavingGoalId());
        RestaurantEntity restaurantEntity = getRestaurantEntity(request.restaurantId());
        Expense expense = request.toExpense();

        ExpenseEntity expenseEntity = new ExpenseEntity(expenseSavingGoalEntity, restaurantEntity, expense);
        ExpenseEntity saved = expenseRepository.save(expenseEntity);
        return saved.getId();
    }

    private ExpenseSavingGoalEntity getExpenseSavingGoalEntity(Long id) {
        return expenseSavingGoalRepository.findById(id)
                .orElseThrow(() -> new HankkiMoaException(
                        ExceptionCode.EXPENSE_SAVING_GOAL_NOT_FOUND));
    }

    private RestaurantEntity getRestaurantEntity(Long id) {
        if (id == null) return null;

        return restaurantRepository.findById(id)
                .orElseThrow(() -> new HankkiMoaException(
                        ExceptionCode.RESTAURANT_NOT_FOUND));
    }
}
