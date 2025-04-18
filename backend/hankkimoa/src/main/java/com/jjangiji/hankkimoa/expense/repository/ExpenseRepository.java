package com.jjangiji.hankkimoa.expense.repository;

import com.jjangiji.hankkimoa.expense.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
