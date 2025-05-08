package com.jjangiji.hankkimoa.expense.repository;


import com.jjangiji.hankkimoa.config.RepositoryTest;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import com.jjangiji.hankkimoa.user.domain.LoginType;
import com.jjangiji.hankkimoa.user.domain.Role;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.Optional;

class ExpenseSavingGoalRepositoryTest extends RepositoryTest {

    @Autowired
    private ExpenseSavingGoalRepository expenseSavingGoalRepository;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("최신 목표 금액 조회")
    @Test
    void findLastByUser() {
        // given
        User user = new User("hankkimoa@gmail.com", "한끼", "hankkiImage", LoginType.KAKAO, Role.USER);
        User savedUser = userRepository.save(user);

        ExpenseSavingGoal expenseSavingGoal1 = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(savedUser,100_000,
                        LocalDate.of(2025, 4, 20),
                        LocalDate.of(2025, 4, 26)));
        ExpenseSavingGoal expenseSavingGoal2 = expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(savedUser,100_000,
                        LocalDate.of(2025, 4, 27),
                        LocalDate.of(2025, 5, 3)));

        expenseSavingGoalRepository.save(expenseSavingGoal1);
        expenseSavingGoalRepository.save(expenseSavingGoal2);

        // when
        Optional<ExpenseSavingGoal> result = expenseSavingGoalRepository.findLastByUser(user);

        // then
        Assertions.assertThat(result.get()).isEqualTo(expenseSavingGoal2);
    }
}
