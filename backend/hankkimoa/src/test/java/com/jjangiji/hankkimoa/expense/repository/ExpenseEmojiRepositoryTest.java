package com.jjangiji.hankkimoa.expense.repository;

import com.jjangiji.hankkimoa.config.RepositoryTest;
import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseEmoji;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import com.jjangiji.hankkimoa.restaurant.domain.Category;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.repository.CategoryRepository;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import com.jjangiji.hankkimoa.user.domain.LoginType;
import com.jjangiji.hankkimoa.user.domain.Role;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;

class ExpenseEmojiRepositoryTest extends RepositoryTest {

    private User user;
    private ExpenseSavingGoal expenseSavingGoal;
    private Restaurant restaurant;
    private Expense expense;
    private final LocalDate now = LocalDate.now();

    @Autowired
    private ExpenseSavingGoalRepository expenseSavingGoalRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpenseEmojiRepository expenseEmojiRepository;

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User("hankkimoa@gmail.com", "한끼", "hankkiImage", LoginType.KAKAO, Role.USER));
        expenseSavingGoal = expenseSavingGoalRepository.save(new ExpenseSavingGoal(user, 80_000,
                LocalDate.now(), LocalDate.now().plusDays(6)));
        Category category = categoryRepository.save(new Category("한식"));
        restaurant  = restaurantRepository.save(new Restaurant(category, "한끼식당", "12345"));
        expense = expenseRepository.save(new Expense(expenseSavingGoal, restaurant,
                "한끼식당", "순두부", 8_000,
                "든든하게 먹음!", now, 5));
    }

    @DisplayName("지출 이모지 존재 여부 반환 성공 : 이미 있는 경우")
    @Test
    void emojiExist() {
        // given
        expenseEmojiRepository.save(new ExpenseEmoji(user, expense, 1));

        // when
        boolean result = expenseEmojiRepository.existsExpenseEmojiByExpenseAndUserAndEmojiId(expense, user, 1);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("지출 이모지 존재 여부 반환 성공 : 없는 경우")
    @Test
    void emojiNotExist() {
        // given & when
        boolean result = expenseEmojiRepository.existsExpenseEmojiByExpenseAndUserAndEmojiId(expense, user, 1);

        // then
        Assertions.assertThat(result).isFalse();
    }
}
