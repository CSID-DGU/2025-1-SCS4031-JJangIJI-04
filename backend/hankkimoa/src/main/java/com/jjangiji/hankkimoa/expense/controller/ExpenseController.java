package com.jjangiji.hankkimoa.expense.controller;

import com.jjangiji.hankkimoa.expense.service.ExpenseService;
import com.jjangiji.hankkimoa.expense.service.dto.response.DailyExpenseResponse;
import com.jjangiji.hankkimoa.expense.service.dto.request.ExpenseCreateRequest;
import com.jjangiji.hankkimoa.expense.service.dto.response.MonthlyExpenseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/api/expenses")
    public ResponseEntity<Void> createExpense(@RequestBody ExpenseCreateRequest request) {
        Long expenseId = expenseService.createExpense(request);
        return ResponseEntity.created(URI.create("/expenses/" + expenseId)).build();
    }

    @GetMapping("/api/users/{userId}/expenses")
    public ResponseEntity<MonthlyExpenseResponse> readMonthlyExpenses(@PathVariable("userId") Long userId,
                                                                      @RequestParam("from") LocalDate from,
                                                                      @RequestParam("to") LocalDate to) {
        MonthlyExpenseResponse monthlyExpenseResponse = expenseService.readMonthlyExpenses(userId, from, to);
        return ResponseEntity.ok(monthlyExpenseResponse);
    }

    @GetMapping("/api/users/{userId}/saving-goals/{savingGoalId}/expenses")
    public ResponseEntity<DailyExpenseResponse> readDailyExpenses(
            @PathVariable("userId") Long userId,
            @PathVariable Long savingGoalId,
            @RequestParam LocalDate date) {
        DailyExpenseResponse dailyExpenseResponse = expenseService.readDailyExpenses(userId, savingGoalId, date);
        return ResponseEntity.ok(dailyExpenseResponse);
    }

    @PostMapping("/api/expenses/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable("expenseId") Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }
}
