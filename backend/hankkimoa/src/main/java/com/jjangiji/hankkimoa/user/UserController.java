package com.jjangiji.hankkimoa.user;

import com.jjangiji.hankkimoa.auth.config.AuthRequiredPrincipal;
import com.jjangiji.hankkimoa.auth.controller.cookie.CookieResolver;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.service.UserService;
import com.jjangiji.hankkimoa.user.service.dto.UserMeResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final CookieResolver cookieResolver;

    @GetMapping("/api/users/me")
    public ResponseEntity<UserMeResponse> getMyInfo(@AuthRequiredPrincipal User user, HttpServletRequest request) {
        UserMeResponse response = userService.getMyInfo(user);
        return ResponseEntity.ok(response);
    }
}
