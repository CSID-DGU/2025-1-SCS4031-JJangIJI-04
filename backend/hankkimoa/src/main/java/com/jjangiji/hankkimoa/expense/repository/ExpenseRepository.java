package com.jjangiji.hankkimoa.expense.repository;

import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByExpenseSavingGoalOrderByExpenseDateAsc(ExpenseSavingGoal expenseSavingGoal);

    @Query("SELECT e FROM Expense e "
            + "JOIN e.expenseSavingGoal esg "
            + "WHERE esg.user.id = :userId AND e.expenseDate BETWEEN :startDate AND :endDate "
            + "ORDER BY e.expenseDate ASC ")
    List<Expense> findAllByExpenseDateOrderByExpenseDateAsc(@Param("userId") Long userId,
                                                            @Param("startDate") LocalDate startDate,
                                                            @Param("endDate") LocalDate endDate);
}
