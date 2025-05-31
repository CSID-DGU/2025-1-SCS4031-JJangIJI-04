package com.jjangiji.hankkimoa.restaurant.repository;

import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Set;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query(value = """
            SELECT * FROM restaurant r
            WHERE MATCH(name) AGAINST(:keyword IN NATURAL LANGUAGE MODE)
            """,
            nativeQuery = true)
    List<Restaurant> findAllByKeyword(@Param("keyword") String keyword);

    @Query("SELECT r.uniqueId FROM Restaurant r")
    Set<String> findAllUniqueId();

    List<Restaurant> findAllByUniqueIdIn(List<String> uniqueIds);
}
