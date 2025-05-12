package com.jjangiji.hankkimoa.user;

import com.jjangiji.hankkimoa.user.domain.Category;
import com.jjangiji.hankkimoa.user.domain.LoginType;
import com.jjangiji.hankkimoa.user.domain.Role;
import com.jjangiji.hankkimoa.user.domain.User;

public record UserMeResponse(
        Long id,
        String email,
        String nickname,
        String imageUrl,
        LoginType loginType,
        Role role,
        Category category
) {
    public static UserMeResponse from(User user) {
        return new UserMeResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getImageUrl(),
                user.getLoginType(),
                user.getRole(),
                user.getCategory()
        );

    }
}
