package com.jjangiji.hankkimoa.restaurant.service.dto.response;

public record RecommendRestaurantResponse(Long id,
                                          Integer menuAverage,
                                          String imgUrl,
                                          String streetAddress,
                                          String openingHours,
                                          String category,
                                          boolean bookmared) {
}
