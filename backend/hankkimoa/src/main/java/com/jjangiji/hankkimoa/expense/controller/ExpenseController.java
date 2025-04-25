package com.jjangiji.hankkimoa.expense.controller;

import com.jjangiji.hankkimoa.expense.service.ExpenseService;
import com.jjangiji.hankkimoa.expense.service.dto.DailyExpenseResponse;
import com.jjangiji.hankkimoa.expense.service.dto.ExpenseCreateRequest;
import com.jjangiji.hankkimoa.expense.service.dto.MonthlyExpenseResponse;
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

    @GetMapping("/api/expenses")
    public ResponseEntity<MonthlyExpenseResponse> readMonthExpenses( // TODO 타유저 정보도 볼 수 있게
                                                                     @RequestParam("from") LocalDate from,
                                                                     @RequestParam("to") LocalDate to) {
        MonthlyExpenseResponse monthlyExpenseResponse = expenseService.readMonthExpenses(from, to);
        return ResponseEntity.ok(monthlyExpenseResponse);
    }

    @GetMapping("/api/saving-goals/{savingGoalId}/expenses")
    public ResponseEntity<DailyExpenseResponse> readDateExpenses(
            @PathVariable Long savingGoalId,
            @RequestParam LocalDate date) {
        DailyExpenseResponse dailyExpenseResponse = expenseService.readDateExpenses(savingGoalId, date);
        return ResponseEntity.ok(dailyExpenseResponse);
    }

    @PostMapping("/api/expenses/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable("expenseId") Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }
}
