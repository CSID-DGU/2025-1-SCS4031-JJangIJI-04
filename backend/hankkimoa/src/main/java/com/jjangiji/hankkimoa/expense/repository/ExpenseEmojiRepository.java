package com.jjangiji.hankkimoa.expense.repository;

import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseEmoji;
import com.jjangiji.hankkimoa.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseEmojiRepository extends JpaRepository<ExpenseEmoji, Long> {

    boolean existsExpenseEmojiByExpenseAndUserAndEmojiId(Expense expense, User user, Integer emojiId);
}
