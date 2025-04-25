package com.jjangiji.hankkimoa.expense.service;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import com.jjangiji.hankkimoa.expense.domain.DailyExpenses;
import com.jjangiji.hankkimoa.expense.domain.SavingGoalStatus;
import com.jjangiji.hankkimoa.expense.repository.ExpenseRepository;
import com.jjangiji.hankkimoa.expense.repository.ExpenseSavingGoalRepository;
import com.jjangiji.hankkimoa.expense.service.dto.DailyExpenseResponse;
import com.jjangiji.hankkimoa.expense.service.dto.ExpenseCreateRequest;
import com.jjangiji.hankkimoa.expense.service.dto.ExpenseResponse;
import com.jjangiji.hankkimoa.expense.service.dto.MonthlyExpenseResponse;
import com.jjangiji.hankkimoa.expense.service.dto.SavingGoalStatusResponse;
import com.jjangiji.hankkimoa.expense.service.dto.SimpleExpenseResponse;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseService {

    private final RestaurantRepository restaurantRepository;
    private final ExpenseSavingGoalRepository expenseSavingGoalRepository;
    private final ExpenseRepository expenseRepository;

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
    public DailyExpenseResponse readDateExpenses(Long savingGoalId, LocalDate date) {
        ExpenseSavingGoal expenseSavingGoal = readExpenseSavingGoal(savingGoalId);

        List<Expense> expenses = expenseRepository.findAllByExpenseSavingGoalOrderByExpenseDateAsc(expenseSavingGoal);
        DailyExpenses dailyExpenses = new DailyExpenses(expenses);
        List<Expense> dailyExpense = expenseRepository.findAllByExpenseDateOrderByCreatedAtDesc(date);

        List<SimpleExpenseResponse> simpleExpenseResponses = toSimpleExpenseResponses(dailyExpenses);
        SavingGoalStatusResponse savingGoalStatusResponse = toSavingGoalStatusResponse(expenseSavingGoal, expenses);
        List<ExpenseResponse> expenseResponses = toExpenseResponses(dailyExpense);
        return new DailyExpenseResponse(simpleExpenseResponses, savingGoalStatusResponse, expenseResponses);
    }

    private List<SimpleExpenseResponse> toSimpleExpenseResponses(DailyExpenses expenseByDates) {
        return expenseByDates.getDailyExpenses().stream()
                .map(expenseByDate -> new SimpleExpenseResponse(
                        expenseByDate.getExpenseDate(),
                        expenseByDate.calculateTotalExpense(),
                        expenseByDate.getExpenseStatus().name()))
                .toList();
    }

    private SavingGoalStatusResponse toSavingGoalStatusResponse(ExpenseSavingGoal expenseSavingGoal, List<Expense> dailyExpenses) {
        int usedPercentage = expenseSavingGoal.calculatePercentage(dailyExpenses);
        return new SavingGoalStatusResponse(
                expenseSavingGoal.getBudget(),
                expenseSavingGoal.calculateRemainingBudget(dailyExpenses),
                usedPercentage,
                SavingGoalStatus.convert(usedPercentage).getMessage()
        );
    }

    private List<ExpenseResponse> toExpenseResponses(List<Expense> dateExpenses) {
        return dateExpenses.stream()
                .map(expense -> new ExpenseResponse(
                        expense.getRestaurantName(), expense.getMenuName(),
                        expense.getExpense(), expense.getMemo()))
                .toList();
    }

    @Transactional(readOnly = true)
    public MonthlyExpenseResponse readMonthExpenses(LocalDate startDate, LocalDate endDate) {
        validateDates(startDate, endDate);
        List<Expense> expenses = expenseRepository.findAllByExpenseDateBetweenOrderByExpenseDateAsc(startDate, endDate);
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
