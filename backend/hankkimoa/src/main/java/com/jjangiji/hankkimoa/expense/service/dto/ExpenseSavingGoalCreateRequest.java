package com.jjangiji.hankkimoa.expense.service.dto;

import java.time.LocalDate;

public record ExpenseSavingGoalCreateRequest(Integer budget,
                                             LocalDate startDate,
                                             LocalDate endDate) {
}
