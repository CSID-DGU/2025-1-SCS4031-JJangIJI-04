package com.jjangiji.hankkimoa.user.dto;

import com.jjangiji.hankkimoa.user.domain.Role;
import com.jjangiji.hankkimoa.user.domain.SocialType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoSignupDto {
    private String socialId;
    private SocialType socialType;
    private Role role;

    private String nickname;
    private String image_url;
    private String category;
    private boolean registered; //user이면 true, 아니면 false

    private String accessToken;
    private String refreshToken;
}
