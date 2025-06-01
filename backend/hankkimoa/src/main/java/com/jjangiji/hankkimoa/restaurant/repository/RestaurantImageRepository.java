package com.jjangiji.hankkimoa.restaurant.repository;

import com.jjangiji.hankkimoa.restaurant.domain.RestaurantImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RestaurantImageRepository extends JpaRepository<RestaurantImage, Long> {

    Optional<RestaurantImage> findByRestaurantId(Long restaurantId);
    List<RestaurantImage> findAllByRestaurantId(Long restaurantId);
}
