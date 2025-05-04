package com.jjangiji.hankkimoa.user.service;

import com.jjangiji.hankkimoa.user.domain.Category;
import com.jjangiji.hankkimoa.user.domain.RefreshToken;
import com.jjangiji.hankkimoa.user.domain.SocialType;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.dto.KakaoProfileDto;
import com.jjangiji.hankkimoa.user.dto.KakaoSignupDto;
import com.jjangiji.hankkimoa.user.dto.TokenDto;
import com.jjangiji.hankkimoa.user.repository.RefreshTokenRepository;
import com.jjangiji.hankkimoa.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class KakaoService {

    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${oauth.kakao.client-id}")
    private String kakaoClientId;

    @Value("${oauth.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    private final UserRepository userRepository;

    public KakaoService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    public TokenDto getAccessToken(String code){
        RestClient restClient = RestClient.create();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("grant_type", "authorization_code");

        ResponseEntity<TokenDto> response =  restClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(params)
                .retrieve()
                .toEntity(TokenDto.class);

        System.out.println("응답 accesstoken JSON " + response.getBody());
        return response.getBody();
    }

    public KakaoProfileDto getKakaoProfile(String token){
        RestClient restClient = RestClient.create();
        ResponseEntity<KakaoProfileDto> response =  restClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer "+token)
                .retrieve()
                .toEntity(KakaoProfileDto.class);
        System.out.println("profile JSON" + response.getBody());
        return response.getBody();
    }

    public User signup(KakaoSignupDto kakaoSignupDto){
        User user = User.builder()
                .socialId(kakaoSignupDto.getSocialId())
                .socialType(SocialType.KAKAO)
                .nickname(kakaoSignupDto.getNickname())
                .image_url(kakaoSignupDto.getImage_url())
                .category(Category.valueOf(kakaoSignupDto.getCategory()))
                .registered(true)
                .build();
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void saveRefreshToken(String socialId, String refreshToken) {
        refreshTokenRepository.deleteBySocialId(socialId);
        RefreshToken newrefreshToken = new RefreshToken();
        newrefreshToken.setSocialId(socialId);
        newrefreshToken.setRefreshToken(refreshToken);
        refreshTokenRepository.save(newrefreshToken);
    }

    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .build();
    }

    public RefreshToken getRefreshToken(String socialId) {
        return refreshTokenRepository.findById(socialId).orElse(null);
    }
}

