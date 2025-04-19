package com.jjangiji.hankkimoa.expense.repository;

import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByExpenseDateOrderByCreatedAtDesc(LocalDate date);
    List<Expense> findAllByExpenseSavingGoalOrderByExpenseDateAsc(ExpenseSavingGoal expenseSavingGoal);
}
