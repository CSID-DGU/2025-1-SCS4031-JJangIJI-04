package com.jjangiji.hankkimoa.expense.service;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.expense.domain.DailyExpenses;
import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseEmoji;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

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
        return expenses.stream()
                .map(expense -> new ExpenseResponse(
                        expense.getRestaurantName(), expense.getMenuName(),
                        expense.getExpense(), expense.getMemo(), toEmojiResponses(expense.getEmojis())
                ))
                .toList();
    }

    private List<EmojiResponse> toEmojiResponses(List<ExpenseEmoji> expenseEmojis) {
        LinkedHashMap<Integer, Long> emojis = expenseEmojis.stream()
                .collect(Collectors.groupingBy(
                        ExpenseEmoji::getEmojiId,
                        LinkedHashMap::new,
                        Collectors.counting()
                ));

        return emojis.entrySet().stream()
                .map(entry -> new EmojiResponse(entry.getKey(), entry.getValue()))
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
        List<CommunityExpenseResponse> result = new ArrayList<>(); // TODO 리팩토링

        List<ExpenseSavingGoal> expenseSavingGoals = expenseSavingGoalRepository.findAllLastExpenseSavingGoalOrderByCreatedAtDESC(size, page);
        for (ExpenseSavingGoal expenseSavingGoal : expenseSavingGoals) {

            List<Expense> savingGoalExpenses = expenseRepository.findAllByExpenseSavingGoal(expenseSavingGoal).stream()
                    .sorted(Comparator.comparing(Expense::getCreatedAt).reversed())
                    .toList();
            User user = expenseSavingGoal.getUser();

            result.add(toCommunityExpenseResponse(user, expenseSavingGoal, savingGoalExpenses));
        }

        return result;
    }

    private CommunityExpenseResponse toCommunityExpenseResponse(User user, ExpenseSavingGoal expenseSavingGoal, List<Expense> savingGoalExpenses) {
        Expense lastExpense = savingGoalExpenses.get(0);
        return new CommunityExpenseResponse(
                user.getNickname(),
                user.getId(),
                user.getImageUrl(),
                expenseSavingGoal.getId(),
                lastExpense.getId(),
                lastExpense.getRestaurantId(),
                lastExpense.getRestaurantName(),
                lastExpense.getMenuName(),
                lastExpense.getExpense(),
                lastExpense.getCreatedAt(),
                expenseSavingGoal.getBudget(),
                expenseSavingGoal.calculateRemainingBudget(savingGoalExpenses),
                lastExpense.getMemo(),
                toEmojiResponses(lastExpense.getEmojis())
        );
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
