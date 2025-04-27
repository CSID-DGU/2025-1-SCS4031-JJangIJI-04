package com.jjangiji.hankkimoa.expense.service.dto;

import java.util.List;

public record DailyExpenseResponse(List<SimpleExpenseResponse> dailyExpensesStatus,
                                   SavingGoalStatusResponse savingGoalStatus,
                                   List<ExpenseResponse> expenses) {
}
