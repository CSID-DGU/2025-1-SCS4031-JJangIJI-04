package com.jjangiji.hankkimoa.expense.controller;

import com.jjangiji.hankkimoa.expense.dto.ExpenseCreateRequest;
import com.jjangiji.hankkimoa.expense.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RequiredArgsConstructor
@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/expenses")
    public ResponseEntity<Void> createExpense(@RequestBody ExpenseCreateRequest request) {
        Long expenseId = expenseService.createExpense(request);
        return ResponseEntity.created(URI.create("/expenses/" + expenseId)).build();
    }
}
