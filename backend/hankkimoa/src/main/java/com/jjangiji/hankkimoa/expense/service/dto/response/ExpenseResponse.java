package com.jjangiji.hankkimoa.expense.service.dto.response;

import java.util.List;

public record ExpenseResponse(String restaurant, String menu,
                              Integer expense, String memo, List<EmojiResponse> emojis) {
}
