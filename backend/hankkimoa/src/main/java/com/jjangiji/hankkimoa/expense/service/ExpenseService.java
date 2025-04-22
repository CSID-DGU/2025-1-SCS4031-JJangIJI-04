package com.jjangiji.hankkimoa.expense.service;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.expense.domain.Expense;
import com.jjangiji.hankkimoa.expense.domain.ExpenseByDate;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import com.jjangiji.hankkimoa.expense.domain.ExpenseStatus;
import com.jjangiji.hankkimoa.expense.domain.SavingGoalStatus;
import com.jjangiji.hankkimoa.expense.repository.ExpenseRepository;
import com.jjangiji.hankkimoa.expense.repository.ExpenseSavingGoalRepository;
import com.jjangiji.hankkimoa.expense.service.dto.DateExpenseResponse;
import com.jjangiji.hankkimoa.expense.service.dto.ExpenseCreateRequest;
import com.jjangiji.hankkimoa.expense.service.dto.ExpenseResponse;
import com.jjangiji.hankkimoa.expense.service.dto.SavingGoalStatusResponse;
import com.jjangiji.hankkimoa.expense.service.dto.SimpleExpenseResponse;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
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

    @Transactional
    public DateExpenseResponse readDateExpenses(Long savingGoalId, LocalDate date) {
        ExpenseSavingGoal expenseSavingGoal = readExpenseSavingGoal(savingGoalId);

        List<Expense> dailyExpenses = expenseRepository.findAllByExpenseSavingGoalOrderByExpenseDateAsc(expenseSavingGoal);
        List<ExpenseByDate> expenseByDates = toExpenseByDate(dailyExpenses);
        List<Expense> dateExpenses = expenseRepository.findAllByExpenseDateOrderByCreatedAtDesc(date);

        List<SimpleExpenseResponse> simpleExpenseResponses = toSimpleExpenseResponses(expenseSavingGoal, expenseByDates);
        SavingGoalStatusResponse savingGoalStatusResponse = toSavingGoalStatusResponse(expenseSavingGoal, dailyExpenses);
        List<ExpenseResponse> expenseResponses = toExpenseResponses(dateExpenses);
        return new DateExpenseResponse(simpleExpenseResponses, savingGoalStatusResponse, expenseResponses);
    }

    private List<ExpenseByDate> toExpenseByDate(List<Expense> dailyExpenses) {
        return dailyExpenses.stream()
                .collect(Collectors.groupingBy(Expense::getExpenseDate, LinkedHashMap::new, Collectors.toList()))
                .entrySet().stream()
                .map(entry -> new ExpenseByDate(entry.getKey(), entry.getValue()))
                .toList();
    }

    private List<SimpleExpenseResponse> toSimpleExpenseResponses(ExpenseSavingGoal expenseSavingGoal, List<ExpenseByDate> expenseByDates) {
        return expenseByDates.stream()
                .map(expenseByDate -> new SimpleExpenseResponse(
                        expenseByDate.getExpenseDate(),
                        expenseByDate.calculateTotalExpense(),
                        ExpenseStatus.convert(expenseSavingGoal, expenseByDate).name()))
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
