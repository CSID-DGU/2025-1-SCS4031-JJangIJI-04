package com.jjangiji.hankkimoa.expense.service;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import com.jjangiji.hankkimoa.expense.repository.ExpenseSavingGoalRepository;
import com.jjangiji.hankkimoa.expense.service.dto.request.ExpenseSavingGoalCreateRequest;
import com.jjangiji.hankkimoa.expense.service.dto.response.ExpenseSavingGoalCreateResponse;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExpenseSavingGoalService {

    private final ExpenseSavingGoalRepository expenseSavingGoalRepository;
    private final UserRepository userRepository;

    @Transactional
    public ExpenseSavingGoalCreateResponse createExpenseSavingGoal(User user, ExpenseSavingGoalCreateRequest request) {
        ExpenseSavingGoal expenseSavingGoal = new ExpenseSavingGoal(user, request.budget(), request.startDate(), request.endDate());
        validateExpenseSavingGoalExist(user, expenseSavingGoal);

        ExpenseSavingGoal savedExpenseSavingGoal = expenseSavingGoalRepository.save(expenseSavingGoal);
        return new ExpenseSavingGoalCreateResponse(savedExpenseSavingGoal.getId());
    }

    private void validateExpenseSavingGoalExist(User user, ExpenseSavingGoal expenseSavingGoal) {
        Optional<ExpenseSavingGoal> optionalExpenseSavingGoal = expenseSavingGoalRepository.findLastByUser(user);
        if (optionalExpenseSavingGoal.isEmpty()) return;

        ExpenseSavingGoal lastExpenseSavingGoal = optionalExpenseSavingGoal.get();
        if (!expenseSavingGoal.isAfter(lastExpenseSavingGoal)) {
            throw new HankkiMoaException(ExceptionCode.EXPENSE_SAVING_GOAL_ALREADY_EXIST);
        }
    }
}
