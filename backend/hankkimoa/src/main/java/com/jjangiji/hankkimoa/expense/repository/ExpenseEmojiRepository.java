package com.jjangiji.hankkimoa.expense.repository;

import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseEmoji;
import com.jjangiji.hankkimoa.expense.service.dto.response.EmojiResponse;
import com.jjangiji.hankkimoa.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ExpenseEmojiRepository extends JpaRepository<ExpenseEmoji, Long> {

    boolean existsExpenseEmojiByExpenseAndUserAndEmojiId(Expense expense, User user, Integer emojiId);

    @Query(value = """
        SELECT emoji_id AS emojiId, CAST(COUNT(*) AS INT) AS count
        FROM expense_emoji
        WHERE expense_id = :expenseId
        GROUP BY emoji_id
    """, nativeQuery = true)
    List<EmojiResponse> countExpenseEmojisByExpenseId(@Param("expenseId") Long expenseId);
}
