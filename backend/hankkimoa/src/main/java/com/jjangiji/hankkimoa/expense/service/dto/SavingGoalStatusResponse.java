package com.jjangiji.hankkimoa.expense.service.dto;

public record SavingGoalStatusResponse(Integer budget, Integer remainingBudget,
                                       Integer remainingPercentage, String message) {
}
