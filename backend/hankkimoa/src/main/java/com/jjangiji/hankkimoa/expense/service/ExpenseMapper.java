package com.jjangiji.hankkimoa.expense.service;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExpenseMapper {

    private final ExpenseRepository expenseRepository;

    public Expense readExpense(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new HankkiMoaException(
                        ExceptionCode.EXPENSE_NOT_FOUND));
    }
}
