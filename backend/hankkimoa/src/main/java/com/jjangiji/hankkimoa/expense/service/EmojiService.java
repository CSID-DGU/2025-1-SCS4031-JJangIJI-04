package com.jjangiji.hankkimoa.expense.service;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseEmoji;
import com.jjangiji.hankkimoa.expense.repository.ExpenseEmojiRepository;
import com.jjangiji.hankkimoa.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EmojiService {

    private final ExpenseMapper expenseMapper;
    private final ExpenseEmojiRepository expenseEmojiRepository;

    @Transactional
    public Long createEmoji(User user, Long expenseId, Integer emojiId) {
        Expense expense = expenseMapper.readExpense(expenseId);
        validateExpenseEmojiExist(user, expense, emojiId);

        ExpenseEmoji expenseEmoji = new ExpenseEmoji(user, expense, emojiId);
        return expenseEmojiRepository.save(expenseEmoji).getId();
    }

    private void validateExpenseEmojiExist(User user, Expense expense, Integer emojiId) {
        if (expenseEmojiRepository.existsExpenseEmojiByExpenseAndUserAndEmojiId(expense, user, emojiId)) {
            throw new HankkiMoaException(ExceptionCode.EMOJI_ALREADY_EXIST);
        }
    }
}
