package com.jjangiji.hankkimoa.auth.controller;

import com.jjangiji.hankkimoa.auth.service.AuthService;
import com.jjangiji.hankkimoa.auth.service.dto.request.OauthLoginRequest;
import com.jjangiji.hankkimoa.auth.service.dto.response.AuthTokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/oauth/login")
    public ResponseEntity<Void> oauthLogin(@Valid @RequestBody OauthLoginRequest request) {
        AuthTokenResponse response = authService.oauthLogin(request);
        return ResponseEntity.ok().build();
    }
}
