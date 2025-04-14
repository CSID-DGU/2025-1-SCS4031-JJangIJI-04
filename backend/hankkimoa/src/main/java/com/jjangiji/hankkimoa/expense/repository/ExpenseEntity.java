package com.jjangiji.hankkimoa.expense.repository;

import com.jjangiji.hankkimoa.common.BaseEntity;
import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.place.repository.RestaurantEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class ExpenseEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ExpenseSavingGoalEntity expenseSavingGoal;

    @ManyToOne(fetch = FetchType.LAZY)
    private RestaurantEntity restaurantEntity;

    @Column(nullable = false)
    private String restaurantName;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private int expense;

    private String memo;

    @Column(nullable = false)
    private LocalDate expenseDate;

    @Column(nullable = false)
    private int rating;

    public ExpenseEntity(ExpenseSavingGoalEntity expenseSavingGoal, RestaurantEntity restaurantEntity,
                         Expense expense) {
        this.expenseSavingGoal = expenseSavingGoal;
        this.restaurantEntity = restaurantEntity;
        this.restaurantName = expense.getRestaurantName();
        this.menuName = expense.getMenuName();
        this.expense = expense.getExpense();
        this.memo = expense.getMemo();
        this.expenseDate = expense.getExpenseDate();
        this.rating = expense.getRating();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ExpenseEntity that = (ExpenseEntity) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
