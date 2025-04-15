package com.jjangiji.hankkimoa.expense.service.dto;

import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import java.time.LocalDate;

public record ExpenseCreateRequest(Long expectSavingGoalId, Long restaurantId,
                                   String restaurantName, String menuName, Integer expense,
                                   String memo, LocalDate expenseDate, Integer rating) {

    public Expense toExpense(ExpenseSavingGoal expenseSavingGoal, Restaurant restaurant) {
        return new Expense(expenseSavingGoal, restaurant, restaurantName, menuName, expense,
                memo, expenseDate, rating);
    }
}
