package com.jjangiji.hankkimoa.expense.service;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import com.jjangiji.hankkimoa.expense.repository.ExpenseSavingGoalRepository;
import com.jjangiji.hankkimoa.expense.service.dto.ExpenseSavingGoalRequest;
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
    public Long createExpenseSavingGoal(Long userId, ExpenseSavingGoalRequest request) {
        User user = readUser(userId);
        ExpenseSavingGoal expenseSavingGoal = new ExpenseSavingGoal(user, request.budget(), request.startDate(), request.endDate());
        validateExpenseSavingGoalExist(user, expenseSavingGoal);

        return expenseSavingGoalRepository.save(expenseSavingGoal).getId();
    }

    private User readUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new HankkiMoaException(ExceptionCode.USER_NOT_FOUND));
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
