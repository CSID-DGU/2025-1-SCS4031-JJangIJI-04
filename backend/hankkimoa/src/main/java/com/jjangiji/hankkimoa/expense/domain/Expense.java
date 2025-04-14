package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import lombok.Getter;
import java.time.LocalDate;
import java.util.Objects;

@Getter
public class Expense {

    private final String restaurantName;
    private final String menuName;
    private final Money expense;
    private final Memo memo;
    private final ExpenseDate expenseDate;
    private final Rating rating;

    public Expense(String restaurantName, String menuName, Money money, Memo memo, ExpenseDate expenseDate,
                   Rating rating) {
        if (restaurantName == null) {
            throw new HankkiMoaException(ExceptionCode.RESTAURANT_NAME_EMPTY);
        }

        if (menuName == null) {
            throw new HankkiMoaException(ExceptionCode.MENU_NAME_EMPTY);
        }

        this.restaurantName = restaurantName;
        this.menuName = menuName;
        this.expense = money;
        this.memo = memo;
        this.expenseDate = expenseDate;
        this.rating = rating;
    }

    public Integer getExpense() {
        return expense.getAmount();
    }

    public String getMemo() {
        return memo.getValue();
    }

    public LocalDate getExpenseDate() {
        return expenseDate.getDate();
    }

    public Integer getRating() {
        return rating.getValue();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Expense expense1 = (Expense) object;
        return expense == expense1.expense && Objects.equals(restaurantName, expense1.restaurantName)
                && Objects.equals(menuName, expense1.menuName) && Objects.equals(memo, expense1.memo)
                && Objects.equals(expenseDate, expense1.expenseDate) && Objects.equals(rating,
                expense1.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantName, menuName, expense, memo, expenseDate, rating);
    }
}
