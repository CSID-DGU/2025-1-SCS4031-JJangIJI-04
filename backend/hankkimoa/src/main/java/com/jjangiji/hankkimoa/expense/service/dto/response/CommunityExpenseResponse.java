package com.jjangiji.hankkimoa.expense.service.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record CommunityExpenseResponse(
        String nickname,
        Long userId,
        String imageUrl,
        Long savingGoalId,
        Long expenseId,
        Long restaurantId,
        String restaurant,
        String menu,
        Integer expense,
        LocalDateTime createdAt,
        Integer savingGoal,
        Integer remainingBudget,
        String memo,
        List<EmojiResponse> emojis
) {
}
