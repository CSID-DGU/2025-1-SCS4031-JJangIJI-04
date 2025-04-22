package com.jjangiji.hankkimoa.expense.controller;

import com.jjangiji.hankkimoa.expense.service.ExpenseService;
import com.jjangiji.hankkimoa.expense.service.dto.DateExpenseResponse;
import com.jjangiji.hankkimoa.expense.service.dto.ExpenseCreateRequest;
import com.jjangiji.hankkimoa.expense.service.dto.MonthExpenseResponse;
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
    public ResponseEntity<MonthExpenseResponse> readMonthExpenses( // TODO 타유저 정보도 볼 수 있게
            @RequestParam("from") LocalDate from,
            @RequestParam("to") LocalDate to) {
        MonthExpenseResponse monthExpenseResponse = expenseService.readMonthExpenses(from, to);
        return ResponseEntity.ok(monthExpenseResponse);
    }

    @GetMapping("/api/saving-goals/{savingGoalId}/expenses")
    public ResponseEntity<DateExpenseResponse> readDateExpenses(
            @PathVariable Long savingGoalId,
            @RequestParam LocalDate date) {
        DateExpenseResponse dateExpenseResponse = expenseService.readDateExpenses(savingGoalId, date);
        return ResponseEntity.ok(dateExpenseResponse);
    }

    @PostMapping("/api/expenses/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable("expenseId") Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }
}
