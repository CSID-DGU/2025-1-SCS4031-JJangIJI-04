package com.jjangiji.hankkimoa.restaurant.repository;

import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Set<Restaurant> findAllByUniqueIdIn(Set<String> uniqueIds);
}
