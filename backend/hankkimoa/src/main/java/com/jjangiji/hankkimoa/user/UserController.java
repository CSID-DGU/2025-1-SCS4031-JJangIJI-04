package com.jjangiji.hankkimoa.user;

import com.jjangiji.hankkimoa.auth.controller.cookie.CookieExtractor;
import com.jjangiji.hankkimoa.auth.service.AuthUser;
import com.jjangiji.hankkimoa.auth.service.jwt.JwtTokenResolver;
import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final JwtTokenResolver jwtTokenResolver;

    @GetMapping("api/users/me")
    public ResponseEntity<UserMeResponse> getMyInfo(HttpServletRequest request) {
        String accessToken = CookieExtractor.extractAccessToken(request)
                .orElseThrow(() -> new HankkiMoaException(ExceptionCode.AUTHENTICATION_TOKEN_EMPTY));

        AuthUser authUser = jwtTokenResolver.resolveAccessToken(accessToken);
        UserMeResponse response = userService.getMyInfo(authUser.id());

        return ResponseEntity.ok(response);
    }
}
