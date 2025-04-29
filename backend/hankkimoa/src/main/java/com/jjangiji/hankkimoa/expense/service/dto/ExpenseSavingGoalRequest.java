package com.jjangiji.hankkimoa.expense.service.dto;

import java.time.LocalDate;

public record ExpenseSavingGoalRequest(Integer budget,
                                       LocalDate startDate,
                                       LocalDate endDate) {
}
