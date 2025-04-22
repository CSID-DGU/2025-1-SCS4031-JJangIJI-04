package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import lombok.Getter;
import java.time.LocalDate;
import java.util.List;

@Getter
public class ExpenseByDate {

    private final LocalDate expenseDate;
    private final List<Expense> expenses;
    private final ExpenseStatus expenseStatus;

    public ExpenseByDate(LocalDate expenseDate, List<Expense> expenses) {
        validateExpenseByDate(expenseDate, expenses);
        this.expenseDate = expenseDate;
        this.expenses = expenses;
        this.expenseStatus = toExpenseStatus();
    }

    private void validateExpenseByDate(LocalDate expenseDate, List<Expense> expenses) {
        if (!expenses.stream().allMatch(expense -> expense.getExpenseDate().equals(expenseDate))) {
            throw new HankkiMoaException(ExceptionCode.EXPENSE_DATE_NOT_SAME);
        }
    }

    private ExpenseStatus toExpenseStatus() {
        return ExpenseStatus.convert(
                getExpenseSavingGoal(),
                calculateTotalExpense());
    }

    public int calculateTotalExpense() {
        return expenses.stream()
                .mapToInt(Expense::getExpense)
                .sum();
    }

    public ExpenseSavingGoal getExpenseSavingGoal() {
        return expenses.get(0).getExpenseSavingGoal();
    }
}
