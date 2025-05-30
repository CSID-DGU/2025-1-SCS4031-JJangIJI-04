package com.jjangiji.hankkimoa.restaurant.service.dto.response;

import java.util.List;

public record RestaurantResponse(Long id,
                                 String name,
                                 Integer menuAverage,
                                 List<String> imgUrl,
                                 String streetAddress,
                                 List<String> openingHour,
                                 String category,
                                 List<MenuResponse> menu,
                                 boolean bookmarked) {
}
