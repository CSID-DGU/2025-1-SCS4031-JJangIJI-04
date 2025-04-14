package com.jjangiji.hankkimoa.expense.dto;

import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseDate;
import com.jjangiji.hankkimoa.expense.domain.Memo;
import com.jjangiji.hankkimoa.expense.domain.Money;
import com.jjangiji.hankkimoa.expense.domain.Rating;
import java.time.LocalDate;

public record ExpenseCreateRequest(Long expectSavingGoalId, Long restaurantId,
                                   String restaurantName, String menuName, Integer expense,
                                   String memo, LocalDate expenseDate, Integer rating) {

    public Expense toExpense() {
        return new Expense(restaurantName, menuName, new Money(expense),
                new Memo(memo), new ExpenseDate(expenseDate), new Rating(rating));
    }
}
