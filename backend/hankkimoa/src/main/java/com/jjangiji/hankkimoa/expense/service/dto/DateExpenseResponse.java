package com.jjangiji.hankkimoa.expense.service.dto;

import java.util.List;

public record DateExpenseResponse(List<SimpleExpenseResponse> dailyExpenseStatus,
                                  SavingGoalStatusResponse savingGoalStatus,
                                  List<ExpenseResponse> expenses) {
}
