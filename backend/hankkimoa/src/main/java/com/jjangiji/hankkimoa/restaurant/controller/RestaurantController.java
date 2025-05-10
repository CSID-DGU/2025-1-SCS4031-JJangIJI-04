package com.jjangiji.hankkimoa.restaurant.controller;

import com.jjangiji.hankkimoa.restaurant.service.RestaurantService;
import com.jjangiji.hankkimoa.restaurant.service.dto.RestaurantCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/api/restaurants")
    public ResponseEntity<Void> createRestaurants(@RequestBody List<RestaurantCreateRequest> request) {
        System.out.println(".... ????");
        restaurantService.createRestaurants(request);
        return ResponseEntity.noContent().build();
    }
}
