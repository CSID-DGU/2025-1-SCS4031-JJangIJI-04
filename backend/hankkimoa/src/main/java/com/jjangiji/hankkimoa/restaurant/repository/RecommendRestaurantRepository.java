package com.jjangiji.hankkimoa.restaurant.repository;

import com.jjangiji.hankkimoa.restaurant.domain.RecommendRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRestaurantRepository extends JpaRepository<RecommendRestaurant, Long> {
}
