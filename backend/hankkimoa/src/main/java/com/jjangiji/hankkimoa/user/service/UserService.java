package com.jjangiji.hankkimoa.user.service;

import com.jjangiji.hankkimoa.restaurant.domain.Category;
import com.jjangiji.hankkimoa.user.service.dto.UserMeResponse;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.domain.UserCategory;
import com.jjangiji.hankkimoa.user.repository.UserCategoryRepository;
import com.jjangiji.hankkimoa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserCategoryRepository userCategoryRepository;

    @Transactional(readOnly = true)
    public UserMeResponse getMyInfo(User user) {
        List<Category> categories = userCategoryRepository.findAllByUser(user).stream()
                .map(UserCategory::getCategory)
                .collect(Collectors.toList());

        return new UserMeResponse(user.getId(), user.getEmail(),
                                    user.getNickname(), user.getImageUrl(),
                                    user.getLoginType(), user.getRole(), categories);
    }
}
