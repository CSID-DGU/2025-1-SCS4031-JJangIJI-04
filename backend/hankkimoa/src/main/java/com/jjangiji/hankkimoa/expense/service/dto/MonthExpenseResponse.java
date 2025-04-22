package com.jjangiji.hankkimoa.expense.service.dto;

import java.util.List;

public record MonthExpenseResponse(List<SimpleExpenseResponse> dailyExpenseStatus,
                                   Integer monthlyExpenseRecordCount,
                                   Integer dailyExpenseOverBudgetCount) {
}
