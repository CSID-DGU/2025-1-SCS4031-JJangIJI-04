package com.jjangiji.hankkimoa.expense.service;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.expense.domain.DailyExpenses;
import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseEmojis;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import com.jjangiji.hankkimoa.expense.domain.SavingGoalStatusMessage;
import com.jjangiji.hankkimoa.expense.repository.ExpenseRepository;
import com.jjangiji.hankkimoa.expense.repository.ExpenseSavingGoalRepository;
import com.jjangiji.hankkimoa.expense.service.dto.request.ExpenseCreateRequest;
import com.jjangiji.hankkimoa.expense.service.dto.response.CommunityExpenseResponse;
import com.jjangiji.hankkimoa.expense.service.dto.response.DailyExpenseResponse;
import com.jjangiji.hankkimoa.expense.service.dto.response.EmojiResponse;
import com.jjangiji.hankkimoa.expense.service.dto.response.ExpenseResponse;
import com.jjangiji.hankkimoa.expense.service.dto.response.MonthlyExpenseResponse;
import com.jjangiji.hankkimoa.expense.service.dto.response.SavingGoalStatusResponse;
import com.jjangiji.hankkimoa.expense.service.dto.response.TodayExpenses;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import com.jjangiji.hankkimoa.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseService {

    private final RestaurantRepository restaurantRepository;
    private final ExpenseSavingGoalRepository expenseSavingGoalRepository;
    private final ExpenseRepository expenseRepository;

    @Transactional
    public Long createExpense(User user, ExpenseCreateRequest request) {
        ExpenseSavingGoal expenseSavingGoal = readExpenseSavingGoal(user.getId(), request.expenseDate());
        Restaurant restaurant = readRestaurant(request.restaurantId());

        Expense expense = request.toExpense(expenseSavingGoal, restaurant);
        Expense saved = expenseRepository.save(expense);
        return saved.getId();
    }

    private Restaurant readRestaurant(Long id) {
        if (id == null) return null;

        return restaurantRepository.findById(id)
                .orElseThrow(() -> new HankkiMoaException(
                        ExceptionCode.RESTAURANT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public TodayExpenses readTodayExpenses(Long userId, LocalDate date) {
        // todo 접근 가능 여부 확인
        ExpenseSavingGoal expenseSavingGoal = readExpenseSavingGoal(userId, date);
        List<Expense> savingGoalExpenses = expenseRepository.findAllByExpenseSavingGoal(expenseSavingGoal);
        List<Expense> todayExpenses = expenseRepository.findAllByExpenseDateOrderByCreatedAtDesc(expenseSavingGoal, date);

        SavingGoalStatusResponse savingGoalStatusResponse = toSavingGoalStatusResponse(expenseSavingGoal, savingGoalExpenses);
        List<ExpenseResponse> expenseResponses = toExpenseResponses(todayExpenses);
        return new TodayExpenses(savingGoalStatusResponse, expenseResponses);
    }

    private ExpenseSavingGoal readExpenseSavingGoal(Long userId, LocalDate date) {
        return expenseSavingGoalRepository.findByUserAndDate(userId, date)
                .orElseThrow(() -> new HankkiMoaException(
                        ExceptionCode.EXPENSE_SAVING_GOAL_NOT_FOUND));
    }

    private List<DailyExpenseResponse> toDailyExpenseResponses(DailyExpenses expenseByDates) {
        return expenseByDates.getDailyExpenses().stream()
                .map(expenseByDate -> new DailyExpenseResponse(
                        expenseByDate.getExpenseDate(),
                        expenseByDate.calculateTotalExpense(),
                        expenseByDate.getExpenseStatus().name()))
                .toList();
    }

    private SavingGoalStatusResponse toSavingGoalStatusResponse(ExpenseSavingGoal expenseSavingGoal, List<Expense> expenses) {
        return new SavingGoalStatusResponse(
                expenseSavingGoal.getBudget(),
                expenseSavingGoal.calculateRemainingBudget(expenses),
                expenseSavingGoal.calculatePercentage(expenses),
                SavingGoalStatusMessage.convert(expenseSavingGoal.calculatePercentage(expenses)).getMessage()
        );
    }

    private List<ExpenseResponse> toExpenseResponses(List<Expense> expenses) {
        List<ExpenseResponse> expenseResponses = new ArrayList<>();

        for (Expense expense : expenses) {
            ExpenseEmojis expenseEmojis = new ExpenseEmojis(expense.getEmojis());
            expenseResponses.add(new ExpenseResponse(
                    expense.getRestaurantName(),
                    expense.getMenuName(),
                    expense.getExpense(),
                    expense.getMemo(),
                    toEmojiResponses(expenseEmojis)));
        }
        return expenseResponses;
    }

    private List<EmojiResponse> toEmojiResponses(ExpenseEmojis expenseEmojis) {
        return expenseEmojis.getEmojiIds()
                .stream()
                .map(emojiId -> {
                    List<Long> userIds = expenseEmojis.getUserIds(emojiId);
                    return new EmojiResponse(emojiId, userIds.size(), userIds);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public MonthlyExpenseResponse readMonthlyExpenses(Long userId, LocalDate startDate, LocalDate endDate) {
        validateDates(startDate, endDate);
        List<Expense> expenses = expenseRepository.findAllByExpenseDateOrderByExpenseDateAsc(userId, startDate, endDate);
        DailyExpenses dailyExpenses = new DailyExpenses(expenses);

        List<DailyExpenseResponse> dailyExpenseResponse = toDailyExpenseResponses(dailyExpenses);
        int monthlyExpenseRecordCount = dailyExpenses.getSize();
        int dailyExpenseOverBudgetCount = dailyExpenses.getExpenseOverBudgetCount();

        return new MonthlyExpenseResponse(dailyExpenseResponse, monthlyExpenseRecordCount, dailyExpenseOverBudgetCount);
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new HankkiMoaException(ExceptionCode.EXPENSE_DATE_INVALID);
        }
    }

    @Transactional(readOnly = true)
    public List<CommunityExpenseResponse> readCommunityExpenses(Integer size, Integer page) {
        List<CommunityExpenseResponse> result = new ArrayList<>();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Expense> expenses = expenseRepository.findAllWithSavingGoalAndUser(pageable);

        for (Expense expense : expenses) {
            ExpenseSavingGoal savingGoal = expense.getExpenseSavingGoal();
            List<Expense> savingGoalExpenses = expenseRepository.findAllByExpenseSavingGoal(savingGoal);
            User user = savingGoal.getUser();
            ExpenseEmojis expenseEmojis = new ExpenseEmojis(expense.getEmojis());

            result.add(new CommunityExpenseResponse(
                    user.getNickname(),
                    user.getId(),
                    user.getImageUrl(),
                    savingGoal.getId(),
                    expense.getId(),
                    expense.getRestaurantId(),
                    expense.getRestaurantName(),
                    expense.getMenuName(),
                    expense.getExpense(),
                    expense.getCreatedAt(),
                    savingGoal.getBudget(),
                    savingGoal.calculateRemainingBudget(savingGoalExpenses),
                    expense.getMemo(),
                    toEmojiResponses(expenseEmojis)));
        }

        return result;
    }

    @Transactional
    public void deleteExpense(Long expenseId) {
        Expense expense = readExpense(expenseId);
        expenseRepository.deleteById(expense.getId());
    }

    private Expense readExpense(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new HankkiMoaException(
                        ExceptionCode.EXPENSE_NOT_FOUND));
    }
}
