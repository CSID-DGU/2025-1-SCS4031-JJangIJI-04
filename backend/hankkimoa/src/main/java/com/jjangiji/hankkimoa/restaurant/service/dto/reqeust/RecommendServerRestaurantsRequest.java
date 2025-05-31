package com.jjangiji.hankkimoa.restaurant.service.dto.reqeust;

import java.util.List;

public record RecommendServerRestaurantsRequest(Long userId,
                                                List<String> userCategory) {
}
