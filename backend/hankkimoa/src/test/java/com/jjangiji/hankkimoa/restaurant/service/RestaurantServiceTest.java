package com.jjangiji.hankkimoa.restaurant.service;

import com.jjangiji.hankkimoa.config.IntegrationTest;
import com.jjangiji.hankkimoa.restaurant.domain.Category;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.repository.CategoryRepository;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RestaurantServiceTest extends IntegrationTest {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    void setUp() {
        category = categoryRepository.save(new Category("한식"));
    }

    @DisplayName("식당 생성 성공")
    @Test
    void createRestaurants() {
        // given
        Restaurant restaurant = restaurantRepository.save(new Restaurant(category, "한끼식당1", "100"));

        // 새로운 값 id : 110
        // 이전에 있던 값 id : 100

        // 일단 식당 정보 모두 가져옴

        // 그리고 id 값 존재하면 update
        // 없으면 추가

        // when
//        new RestaurantCreateRequest(restaurant.getUniqueId(), restaurant.getName(),
//                restaurant.getCategory().getName(),
//                null, null, null);

        // then
        // 1개 추가 확인

    }
}
