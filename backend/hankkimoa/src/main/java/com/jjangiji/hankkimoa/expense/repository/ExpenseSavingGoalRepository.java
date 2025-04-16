package com.jjangiji.hankkimoa.expense.repository;

import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseSavingGoalRepository extends JpaRepository<ExpenseSavingGoal, Long> {
}
