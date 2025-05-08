package com.jjangiji.hankkimoa.expense.service.dto.request;

import java.time.LocalDate;

public record ExpenseSavingGoalCreateRequest(Integer budget,
                                             LocalDate startDate,
                                             LocalDate endDate) {
}
