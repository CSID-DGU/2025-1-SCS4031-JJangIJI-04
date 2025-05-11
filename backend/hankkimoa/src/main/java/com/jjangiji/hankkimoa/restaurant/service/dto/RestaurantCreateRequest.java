package com.jjangiji.hankkimoa.restaurant.service.dto;

import java.util.List;

public record RestaurantCreateRequest(String id, String name, String category, String address, Integer menu_average, List<String> images,
                                      List<OpeningHoursRequest> openingHours,
                                      List<MenuRequest> menus) {
}
