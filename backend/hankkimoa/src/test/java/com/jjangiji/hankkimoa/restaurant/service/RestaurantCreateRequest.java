package com.jjangiji.hankkimoa.restaurant.service;

import java.util.List;

public record RestaurantCreateRequest(String id, String name, String category, String address, List<String> images,
                                      OpeningHoursRequest openingHours,
                                      MenuRequest menu) {
}
