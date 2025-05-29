package com.jjangiji.hankkimoa.restaurant.controller;

import com.jjangiji.hankkimoa.auth.config.AuthRequiredPrincipal;
import com.jjangiji.hankkimoa.restaurant.service.RestaurantService;
import com.jjangiji.hankkimoa.restaurant.service.dto.RecommendRestaurantResponse;
import com.jjangiji.hankkimoa.restaurant.service.dto.RestaurantCreateRequest;
import com.jjangiji.hankkimoa.restaurant.service.dto.RestaurantSearchResponse;
import com.jjangiji.hankkimoa.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/api/restaurants")
    public ResponseEntity<Void> createRestaurants(@RequestBody List<RestaurantCreateRequest> request) {
        restaurantService.createRestaurants(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/restaurants/search")
    public ResponseEntity<List<RestaurantSearchResponse>> readRestaurants(@RequestParam("keyword") String keyword) {
        List<RestaurantSearchResponse> restaurants = restaurantService.searchRestaurants(keyword);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/api/recommendation/restaurants")
    public ResponseEntity<List<RecommendRestaurantResponse>> readRecommendRestaurants(@AuthRequiredPrincipal User user) {
        List<RecommendRestaurantResponse> recommendRestaurants = restaurantService.readRecommendRestaurants(user);
        return ResponseEntity.ok(recommendRestaurants);
    }
}
