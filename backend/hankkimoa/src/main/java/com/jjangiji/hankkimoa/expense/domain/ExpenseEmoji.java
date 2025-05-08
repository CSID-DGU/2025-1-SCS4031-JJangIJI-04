package com.jjangiji.hankkimoa.expense.domain;

import com.jjangiji.hankkimoa.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class ExpenseEmoji {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Expense expense;

    private Integer emojiId;

    public ExpenseEmoji(Long id, User user, Expense expense, Integer emojiId) {
        this.id = id;
        this.user = user;
        this.expense = expense;
        this.emojiId = emojiId;
    }

    public ExpenseEmoji(User user, Expense expense, Integer emojiId) {
        this.user = user;
        this.expense = expense;
        this.emojiId = emojiId;
    }
}
