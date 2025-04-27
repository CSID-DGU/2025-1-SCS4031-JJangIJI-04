package com.jjangiji.hankkimoa.expense.service.dto;

import java.util.List;

public record MonthlyExpenseResponse(List<SimpleExpenseResponse> dailyExpenseStatus,
                                     Integer monthlyExpenseRecordCount,
                                     Integer dailyExpenseOverBudgetCount) {
}
