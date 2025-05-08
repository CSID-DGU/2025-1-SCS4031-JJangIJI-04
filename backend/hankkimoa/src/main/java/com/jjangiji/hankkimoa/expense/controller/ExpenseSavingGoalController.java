package com.jjangiji.hankkimoa.expense.controller;

import com.jjangiji.hankkimoa.expense.service.ExpenseSavingGoalService;
import com.jjangiji.hankkimoa.expense.service.dto.request.ExpenseSavingGoalCreateRequest;
import com.jjangiji.hankkimoa.expense.service.dto.response.ExpenseSavingGoalCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ExpenseSavingGoalController {

    private final ExpenseSavingGoalService expenseSavingGoalService;

    @PostMapping("/api/users/{userId}/saving-goals")
    public ResponseEntity<ExpenseSavingGoalCreateResponse> createExpense(
            @PathVariable("userId") Long userId,
            @RequestBody ExpenseSavingGoalCreateRequest request) {
        ExpenseSavingGoalCreateResponse response = expenseSavingGoalService.createExpenseSavingGoal(userId, request);
        return ResponseEntity.ok(response);
    }
}
