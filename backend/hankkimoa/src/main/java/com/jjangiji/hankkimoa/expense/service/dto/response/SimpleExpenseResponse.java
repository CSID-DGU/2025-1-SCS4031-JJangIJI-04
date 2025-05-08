package com.jjangiji.hankkimoa.expense.service.dto.response;

import java.time.LocalDate;

public record SimpleExpenseResponse(LocalDate date, Integer totalExpense, String status) {
}
