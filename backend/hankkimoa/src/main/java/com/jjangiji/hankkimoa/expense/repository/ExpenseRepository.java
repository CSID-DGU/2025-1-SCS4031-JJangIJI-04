package com.jjangiji.hankkimoa.expense.repository;

import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT e FROM Expense e "
            + "JOIN e.expenseSavingGoal esg "
            + "WHERE esg.user.id = :userId AND e.expenseDate BETWEEN :startDate AND :endDate "
            + "ORDER BY e.expenseDate ASC ")
    List<Expense> findAllByExpenseDateOrderByExpenseDateAsc(@Param("userId") Long userId,
                                                            @Param("startDate") LocalDate startDate,
                                                            @Param("endDate") LocalDate endDate);

    @Query("SELECT e FROM Expense e "
            + "LEFT JOIN FETCH e.emojis "
            + "WHERE e.expenseSavingGoal = :expenseSavingGoal AND e.expenseDate = :expenseDate "
            + "ORDER BY e.createdAt DESC ")
    List<Expense> findAllByExpenseDateOrderByCreatedAtDesc(@Param("expenseSavingGoal") ExpenseSavingGoal expenseSavingGoal,
                                                           @Param("expenseDate") LocalDate expenseDate);

    List<Expense> findAllByExpenseSavingGoal(ExpenseSavingGoal expenseSavingGoal);
    List<Expense> findAllByExpenseSavingGoalOrderByCreatedAtDesc(ExpenseSavingGoal expenseSavingGoal);
}
