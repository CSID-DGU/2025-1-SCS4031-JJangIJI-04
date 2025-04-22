package com.jjangiji.hankkimoa.expense.domain;

import lombok.Getter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ExpensesByDate {

    private final List<ExpenseByDate> expenseByDates;

    public ExpensesByDate(List<Expense> expenses) {
        this.expenseByDates = expenses.stream()
                .collect(Collectors.groupingBy(Expense::getExpenseDate, LinkedHashMap::new, Collectors.toList()))
                .entrySet().stream()
                .map(entry -> new ExpenseByDate(entry.getKey(), entry.getValue()))
                .toList();
    }

    public int getSize() {
        return expenseByDates.size();
    }

    public int getExpenseOverBudgetCount() {
        return (int) expenseByDates.stream()
                .filter(expenseByDate -> expenseByDate.getExpenseStatus().equals(ExpenseStatus.BAD))
                .count();
    }
}
