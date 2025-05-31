package com.jjangiji.hankkimoa.user.service;

import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.repository.UserCategoryRepository;
import com.jjangiji.hankkimoa.user.repository.UserRepository;
import com.jjangiji.hankkimoa.user.service.dto.CategoryResponse;
import com.jjangiji.hankkimoa.user.service.dto.UserMeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserCategoryRepository userCategoryRepository;

    @Transactional(readOnly = true)
    public UserMeResponse getMyInfo(User user) {
        List<CategoryResponse> categories = userCategoryRepository.findAllByUser(user).stream()
                .map(userCategory -> new CategoryResponse(userCategory.getCategory().getId(), userCategory.getCategoryName()))
                .toList();

        return new UserMeResponse(user.getId(), user.getEmail(),
                                    user.getNickname(), user.getImageUrl(),
                                    user.getLoginType(), user.getRole(), categories);
    }
}
