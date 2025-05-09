package com.jjangiji.hankkimoa.auth.service;

import com.jjangiji.hankkimoa.auth.service.dto.request.OauthLoginRequest;
import com.jjangiji.hankkimoa.auth.service.dto.response.AuthTokenResponse;
import com.jjangiji.hankkimoa.auth.service.dto.response.OauthInfoApiResponse;
import com.jjangiji.hankkimoa.auth.service.jwt.JwtTokenProvider;
import com.jjangiji.hankkimoa.auth.service.oauth.OauthClient;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final OauthClient oauthClient;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthTokenResponse oauthLogin(OauthLoginRequest request) {
        OauthInfoApiResponse oauthInfo = oauthClient.requestOauthInfo(request);

        User user = userRepository.findByEmail(oauthInfo.kakao_account().email())
                .orElseGet(() -> userRepository.save(oauthInfo.toUserEntity()));

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);
        return new AuthTokenResponse(user.getNickname(), user.getImageUrl(), accessToken, refreshToken);
    }
}
