package com.jjangiji.hankkimoa.expense.service.dto;

public record ExpenseResponse(String restaurant, String menu,
                              Integer expense, String memo) {
}
