package com.jjangiji.hankkimoa.user.controller;

import com.jjangiji.hankkimoa.common.auth.JwtTokenProvider;
import com.jjangiji.hankkimoa.user.domain.RefreshToken;
import com.jjangiji.hankkimoa.user.domain.Role;
import com.jjangiji.hankkimoa.user.domain.SocialType;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.dto.KakaoProfileDto;
import com.jjangiji.hankkimoa.user.dto.KakaoSignupDto;
import com.jjangiji.hankkimoa.user.dto.RedirectDto;
import com.jjangiji.hankkimoa.user.dto.TokenDto;
import com.jjangiji.hankkimoa.user.service.KakaoService;
import com.jjangiji.hankkimoa.user.service.UserService;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, KakaoService kakaoService, JwtTokenProvider jwtTokenProvider, SmartInitializingSingleton smartInitializingSingleton) {
        this.kakaoService = kakaoService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }


    @PostMapping("/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestBody RedirectDto redirectDto){
        TokenDto tokenDto = kakaoService.getAccessToken(redirectDto.getCode());
        KakaoProfileDto kakaoProfileDto  = kakaoService.getKakaoProfile(tokenDto.getAccess_token());
        User originalUser = userService.getUserBySocialId(kakaoProfileDto.getSocialId());
        if(originalUser == null){ //새로운 사용자인 경우
            KakaoSignupDto kakaoSignupDto = new KakaoSignupDto();
            kakaoSignupDto.setSocialId(kakaoProfileDto.getSocialId());
            kakaoSignupDto.setSocialType(SocialType.KAKAO);
            kakaoSignupDto.setRole(Role.USER);
            kakaoSignupDto.setNickname(kakaoProfileDto.getNickname());
            kakaoSignupDto.setImage_url(kakaoProfileDto.getImage_url());
            kakaoSignupDto.setRegistered(false);
            return new ResponseEntity<>(kakaoSignupDto, HttpStatus.FOUND);
        }
        RefreshToken savedToken = kakaoService.getRefreshToken(originalUser.getSocialId());

        // 리프레시 토큰이 없거나 만료된 경우
        if (savedToken == null || jwtTokenProvider.isExpired(savedToken.getRefreshToken())) {
            // /refresh로 리다이렉트
            Map<String, Object> response = new HashMap<>();
            response.put("redirectToRefresh", true);
            response.put("socialId", originalUser.getSocialId());
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        }

        String accessToken = jwtTokenProvider.createAccessToken(originalUser.getSocialId(), originalUser.getRole().toString());
        String refreshToken = savedToken.getRefreshToken();

        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("accessToken", accessToken);

        ResponseCookie refreshTokenCookie = kakaoService.createRefreshTokenCookie(refreshToken);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(loginInfo);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody KakaoSignupDto kakaoSignupDto) {
        try {
            User newUser = kakaoService.signup(kakaoSignupDto);
            String accessToken = jwtTokenProvider.createAccessToken(newUser.getSocialId(), newUser.getRole().toString());
            String refreshToken = jwtTokenProvider.createRefreshToken(newUser.getSocialId(), newUser.getRole().toString());
            kakaoService.saveRefreshToken(newUser.getSocialId(), refreshToken);
            Map<String, Object> signupInfo = new HashMap<>();
            signupInfo.put("socialId", newUser.getSocialId());
            signupInfo.put("accessToken", accessToken);
            signupInfo.put("refreshToken", refreshToken);
            ResponseCookie refreshTokenCookie = kakaoService.createRefreshTokenCookie(refreshToken);
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                    .body(signupInfo);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "회원가입 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }

    }
//
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam String socialId) {
        User user = userService.getUserBySocialId(socialId);

        if (user == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "사용자 정보를 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        // 새 토큰 발급
        String newAccessToken = jwtTokenProvider.createAccessToken(socialId, user.getRole().toString());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(socialId, user.getRole().toString());

        // 새 리프레시 토큰 저장
        kakaoService.saveRefreshToken(socialId, newRefreshToken);

        Map<String, Object> tokenInfo = new HashMap<>();
        tokenInfo.put("accessToken", newAccessToken);

        ResponseCookie refreshTokenCookie = kakaoService.createRefreshTokenCookie(newRefreshToken);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(tokenInfo);

    }
}
