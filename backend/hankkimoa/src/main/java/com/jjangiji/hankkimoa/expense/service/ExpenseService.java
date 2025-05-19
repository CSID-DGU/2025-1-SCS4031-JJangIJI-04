package com.jjangiji.hankkimoa.expense.service;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.expense.domain.DailyExpenses;
import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import com.jjangiji.hankkimoa.expense.domain.SavingGoalStatus;
import com.jjangiji.hankkimoa.expense.repository.ExpenseEmojiRepository;
import com.jjangiji.hankkimoa.expense.repository.ExpenseRepository;
import com.jjangiji.hankkimoa.expense.repository.ExpenseSavingGoalRepository;
import com.jjangiji.hankkimoa.expense.service.dto.request.ExpenseCreateRequest;
import com.jjangiji.hankkimoa.expense.service.dto.response.DailyExpenseResponse;
import com.jjangiji.hankkimoa.expense.service.dto.response.EmojiResponse;
import com.jjangiji.hankkimoa.expense.service.dto.response.ExpenseResponse;
import com.jjangiji.hankkimoa.expense.service.dto.response.MonthlyExpenseResponse;
import com.jjangiji.hankkimoa.expense.service.dto.response.SavingGoalStatusResponse;
import com.jjangiji.hankkimoa.expense.service.dto.response.SimpleExpenseResponse;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
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
    private final ExpenseEmojiRepository expenseEmojiRepository;

    @Transactional
    public Long createExpense(ExpenseCreateRequest request) {
        // todo 유저 목표 금액여부 확인
        ExpenseSavingGoal expenseSavingGoal = readExpenseSavingGoal(request.expectSavingGoalId());
        Restaurant restaurant = readRestaurant(request.restaurantId());
        Expense expense = request.toExpense(expenseSavingGoal, restaurant);

        Expense saved = expenseRepository.save(expense);
        return saved.getId();
    }

    private ExpenseSavingGoal readExpenseSavingGoal(Long id) {
        return expenseSavingGoalRepository.findById(id)
                .orElseThrow(() -> new HankkiMoaException(
                        ExceptionCode.EXPENSE_SAVING_GOAL_NOT_FOUND));
    }

    private Restaurant readRestaurant(Long id) {
        if (id == null) return null;

        return restaurantRepository.findById(id)
                .orElseThrow(() -> new HankkiMoaException(
                        ExceptionCode.RESTAURANT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public DailyExpenseResponse readDailyExpenses(Long userId, LocalDate date) {
        // todo 접근 가능 여부 확인
        ExpenseSavingGoal expenseSavingGoal = readExpenseSavingGoal(userId, date);
        List<Expense> savingGoalExpenses = expenseRepository.findAllByExpenseSavingGoalOrderByExpenseDateAsc(expenseSavingGoal);
        DailyExpenses dailyExpenses = new DailyExpenses(savingGoalExpenses);

        List<SimpleExpenseResponse> simpleExpenseResponses = toSimpleExpenseResponses(dailyExpenses);
        SavingGoalStatusResponse savingGoalStatusResponse = toSavingGoalStatusResponse(expenseSavingGoal, dailyExpenses);

        List<Expense> todayExpenses = dailyExpenses.getExpensesDescending(date);
        List<ExpenseResponse> expenseResponses = toExpenseResponses(todayExpenses);
        return new DailyExpenseResponse(simpleExpenseResponses, savingGoalStatusResponse, expenseResponses);
    }

    private ExpenseSavingGoal readExpenseSavingGoal(Long userId, LocalDate date) {
        return expenseSavingGoalRepository.findByUserAndDate(userId, date)
                .orElseThrow(() -> new HankkiMoaException(
                        ExceptionCode.EXPENSE_SAVING_GOAL_NOT_FOUND));
    }

    private List<SimpleExpenseResponse> toSimpleExpenseResponses(DailyExpenses expenseByDates) {
        return expenseByDates.getDailyExpenses().stream()
                .map(expenseByDate -> new SimpleExpenseResponse(
                        expenseByDate.getExpenseDate(),
                        expenseByDate.calculateTotalExpense(),
                        expenseByDate.getExpenseStatus().name()))
                .toList();
    }

    private SavingGoalStatusResponse toSavingGoalStatusResponse(ExpenseSavingGoal expenseSavingGoal, DailyExpenses dailyExpenses) {
        List<Expense> expenses = dailyExpenses.getExpenses();

        int usedPercentage = expenseSavingGoal.calculatePercentage(expenses);
        return new SavingGoalStatusResponse(
                expenseSavingGoal.getBudget(),
                expenseSavingGoal.calculateRemainingBudget(expenses),
                usedPercentage,
                SavingGoalStatus.convert(usedPercentage).getMessage()
        );
    }

    private List<ExpenseResponse> toExpenseResponses(List<Expense> dateExpenses) {
        List<ExpenseResponse> responses = new ArrayList<>();

        for (Expense expense : dateExpenses) {
            List<EmojiResponse> expenseEmojis = expenseEmojiRepository.countExpenseEmojisByExpenseId(expense.getId());

            responses.add(new ExpenseResponse(
                    expense.getRestaurantName(), expense.getMenuName(),
                    expense.getExpense(), expense.getMemo(), expenseEmojis));
        }

        return responses;
    }

    @Transactional(readOnly = true)
    public MonthlyExpenseResponse readMonthlyExpenses(Long userId, LocalDate startDate, LocalDate endDate) {
        validateDates(startDate, endDate);
        List<Expense> expenses = expenseRepository.findAllByExpenseDateOrderByExpenseDateAsc(userId, startDate, endDate);
        DailyExpenses dailyExpenses = new DailyExpenses(expenses);

        List<SimpleExpenseResponse> simpleExpenseResponses = toSimpleExpenseResponses(dailyExpenses);
        int monthlyExpenseRecordCount = dailyExpenses.getSize();
        int dailyExpenseOverBudgetCount = dailyExpenses.getExpenseOverBudgetCount();

        return new MonthlyExpenseResponse(simpleExpenseResponses, monthlyExpenseRecordCount, dailyExpenseOverBudgetCount);
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new HankkiMoaException(ExceptionCode.EXPENSE_DATE_INVALID);
        }
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
