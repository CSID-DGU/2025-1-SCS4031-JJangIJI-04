package com.jjangiji.hankkimoa.expense.service;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.config.IntegrationTest;
import com.jjangiji.hankkimoa.expense.repository.ExpenseSavingGoalRepository;
import com.jjangiji.hankkimoa.expense.service.dto.ExpenseSavingGoalCreateRequest;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;


class ExpenseSavingGoalTest extends IntegrationTest {

    @Autowired
    private ExpenseSavingGoalService expenseSavingGoalService;
    @Autowired
    private ExpenseSavingGoalRepository expenseSavingGoalRepository;
    @Autowired
    private UserRepository userRepository;

    private User user;
    private final LocalDate now = LocalDate.now();
    private final LocalDate sevenDayAfter = now.plusDays(6);

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User("한끼", "hankki"));
    }

    @AfterEach
    void tearDown() {
        expenseSavingGoalRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @DisplayName("지출 목표 금액 추가 성공")
    @Test
    void createExpenseSavingGoal() {
        // given
        ExpenseSavingGoalCreateRequest request = new ExpenseSavingGoalCreateRequest(100_000, now, sevenDayAfter);

        // when & then
        Assertions.assertThatCode(() -> expenseSavingGoalService.createExpenseSavingGoal(user.getId(), request))
                .doesNotThrowAnyException();;
    }

    @DisplayName("지출 목표 금액 추가 실패 : 이미 지출이 존재할 때")
    @Test
    void failWhenExpenseSavingGoalAlreadyExist() {
        // given
        ExpenseSavingGoalCreateRequest request = new ExpenseSavingGoalCreateRequest(100_000, now, sevenDayAfter);
        expenseSavingGoalService.createExpenseSavingGoal(user.getId(), request);

        // when & then
        Assertions.assertThatCode(() -> expenseSavingGoalService.createExpenseSavingGoal(user.getId(), request))
                .isInstanceOf(HankkiMoaException.class)
                .hasMessage(ExceptionCode.EXPENSE_SAVING_GOAL_ALREADY_EXIST.getMessage());
    }
}
