package com.jjangiji.hankkimoa.expense.service.dto;

import java.time.LocalDate;

public record SimpleExpenseResponse(LocalDate date, Integer totalExpense, String status) {
}
