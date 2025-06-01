package com.jjangiji.hankkimoa.user.controller;

import com.jjangiji.hankkimoa.auth.config.AuthRequiredPrincipal;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.service.UserService;
import com.jjangiji.hankkimoa.user.service.dto.CategoryUpdateRequest;
import com.jjangiji.hankkimoa.user.service.dto.UserMeResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users/me")
    public ResponseEntity<UserMeResponse> readMyInfo(@AuthRequiredPrincipal User user, HttpServletRequest request) {
        UserMeResponse response = userService.readMyInfo(user);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/categories")
    public ResponseEntity<Void> updateCategories(@AuthRequiredPrincipal User user, CategoryUpdateRequest request) {
        userService.updateCategories(user, request);
        return ResponseEntity.noContent().build();
    }
}
