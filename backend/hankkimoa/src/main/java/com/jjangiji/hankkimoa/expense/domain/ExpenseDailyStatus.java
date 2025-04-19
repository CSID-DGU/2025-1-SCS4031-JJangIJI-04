package com.jjangiji.hankkimoa.expense.domain;

import lombok.Getter;
import java.util.Arrays;

@Getter
public enum ExpenseDailyStatus {

    GOOD(1000, Integer.MAX_VALUE),
    NOT_BAD(-1000, 1000),
    BAD(Integer.MIN_VALUE, -1000),
    ;

    private final int minDifference;
    private final int maxDifference;

    ExpenseDailyStatus(int minDifference, int maxDifference) {
        this.minDifference = minDifference;
        this.maxDifference = maxDifference;
    }

    public static ExpenseDailyStatus convert(ExpenseSavingGoal expenseSavingGoal, ExpenseByDate expenseByDate) {
        int budgetDifference = (expenseSavingGoal.getBudget() / expenseSavingGoal.getDays()) - expenseByDate.calculateTotalExpense();
        return Arrays.stream(values())
                .filter(status -> status.minDifference < budgetDifference && budgetDifference <= status.maxDifference)
                .findFirst()
                .orElse(BAD);
    }
}
