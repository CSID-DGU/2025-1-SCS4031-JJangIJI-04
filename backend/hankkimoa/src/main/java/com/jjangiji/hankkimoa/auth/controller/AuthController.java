package com.jjangiji.hankkimoa.auth.controller;

import com.jjangiji.hankkimoa.auth.controller.cookie.CookieProvider;
import com.jjangiji.hankkimoa.auth.service.AuthService;
import com.jjangiji.hankkimoa.auth.service.dto.response.AuthResponse;
import com.jjangiji.hankkimoa.auth.service.dto.request.OauthLoginRequest;
import com.jjangiji.hankkimoa.auth.service.dto.response.AuthTokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final CookieProvider cookieProvider;

    @PostMapping("/api/auth/kakao")
    public ResponseEntity<AuthResponse> oauthLogin(@Valid @RequestBody OauthLoginRequest request) {
        AuthTokenResponse response = authService.oauthLogin(request);

        ResponseCookie accessTokenCookie = cookieProvider.createAccessTokenCookie(response.accessToken());
        ResponseCookie refreshTokenCookie = cookieProvider.createRefreshTokenCookie(response.refreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(new AuthResponse(response.nickname(), response.imageUrl()));
    }
}
