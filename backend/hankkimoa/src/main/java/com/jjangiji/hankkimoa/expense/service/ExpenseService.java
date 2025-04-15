package com.jjangiji.hankkimoa.expense.service;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import com.jjangiji.hankkimoa.expense.repository.ExpenseRepository;
import com.jjangiji.hankkimoa.expense.repository.ExpenseSavingGoalRepository;
import com.jjangiji.hankkimoa.expense.service.dto.ExpenseCreateRequest;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
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
        ExpenseSavingGoal expenseSavingGoal = readExpenseSavingGoal(request.expectSavingGoalId());
        Restaurant restaurant = readRestaurant(request.restaurantId());
        Expense expense = request.toExpense(expenseSavingGoal, restaurant);

        Expense saved = expenseRepository.save(expense);
        return saved.getId();
    }

    private ExpenseSavingGoal readExpenseSavingGoal(Long id) {
        return expenseSavingGoalRepository.findById(id)
                .orElseThrow(() -> new HankkiMoaException(
                        ExceptionCode.EXPENSE_SAVING_GOAL_NOT_FOUND));
    }

    private Restaurant readRestaurant(Long id) {
        if (id == null) return null;

        return restaurantRepository.findById(id)
                .orElseThrow(() -> new HankkiMoaException(
                        ExceptionCode.RESTAURANT_NOT_FOUND));
    }
}
