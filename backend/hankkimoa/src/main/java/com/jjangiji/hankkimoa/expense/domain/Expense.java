package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.common.BaseEntity;
import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Expense extends BaseEntity {

    private static final int MEMO_MAX_LENGTH = 100;
    private static final int EXPENSE_MIN = 0;
    private static final int RATING_MIN = 0;
    private static final int RATING_MAX = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ExpenseSavingGoal expenseSavingGoal;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurantEntity;

    @NotNull(message = "식당이름이 NULL일 수 없습니다.")
    private String restaurantName;

    @NotNull(message = "메뉴이름이 NULL일 수 없습니다.")
    private String menuName;

    @NotNull(message = "비용이 NULL일 수 없습니다.")
    private Integer expense;

    private String memo;

    @NotNull(message = "지출일이 NULL일 수 없습니다.")
    private LocalDate expenseDate;

    @NotNull(message = "평점이 NULL일 수 없습니다.")
    private Integer rating;

    public Expense(ExpenseSavingGoal expenseSavingGoal, Restaurant restaurant, String restaurantName,
                   String menuName,
                   Integer expense, String memo, LocalDate expenseDate, Integer rating) {
        validateMemo(memo);
        validateExpense(expense);
        validateExpenseDate(expenseDate);
        validateRating(rating);
        this.expenseSavingGoal = expenseSavingGoal;
        this.restaurantEntity = restaurant;
        this.restaurantName = restaurantName;
        this.menuName = menuName;
        this.expense = expense;
        this.memo = memo;
        this.expenseDate = expenseDate;
        this.rating = rating;
    }

    private void validateMemo(String memo) {
        if (memo != null && memo.length() > MEMO_MAX_LENGTH) {
            throw new HankkiMoaException(ExceptionCode.MEMO_INVALID_LENGTH);
        }
    }

    private void validateExpense(Integer expense) {
        if (expense < EXPENSE_MIN) {
            throw new HankkiMoaException(ExceptionCode.MONEY_NEGATIVE);
        }
    }

    private void validateExpenseDate(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            throw new HankkiMoaException(ExceptionCode.EXPENSE_DATE_INVALID);
        }
    }

    private void validateRating(Integer rating) {
        if (rating < RATING_MIN || rating > RATING_MAX) {
            throw new HankkiMoaException(ExceptionCode.RATING_INVALID_FORMAT);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Expense that = (Expense) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
