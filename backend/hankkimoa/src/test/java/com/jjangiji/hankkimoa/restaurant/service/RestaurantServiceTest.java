package com.jjangiji.hankkimoa.restaurant.service;

import com.jjangiji.hankkimoa.config.IntegrationTest;
import com.jjangiji.hankkimoa.restaurant.domain.Address;
import com.jjangiji.hankkimoa.restaurant.domain.CategoryDictionary;
import com.jjangiji.hankkimoa.restaurant.domain.Category;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.repository.CategoryRepository;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import com.jjangiji.hankkimoa.restaurant.service.dto.MenuRequest;
import com.jjangiji.hankkimoa.restaurant.service.dto.RestaurantCreateRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

class RestaurantServiceTest extends IntegrationTest {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;
    private Address address = new Address(0, 0, "서울 중구 퇴계로18길 20");

    @BeforeEach
    void setUp() {
        category = categoryRepository.save(new Category(CategoryDictionary.한식));
    }

    @DisplayName("식당 생성 성공")
    @Test
    void createRestaurants() {
        // given
        Restaurant restaurant1 = new Restaurant(category, "한끼식당1", "100", 10000, address);
        Restaurant restaurant2 = new Restaurant(category, "한끼식당2", "110", 10000, address);

        // when
        MenuRequest menuRequest = new MenuRequest(true, "돈가스", null, 13000, null);
        RestaurantCreateRequest request1 = new RestaurantCreateRequest(restaurant1.getUniqueId(),
                restaurant1.getName(),
                restaurant1.getCategoryName(),
                null, 10000, null, null, List.of(menuRequest));
        RestaurantCreateRequest request2 = new RestaurantCreateRequest(restaurant2.getUniqueId(),
                restaurant2.getName(),
                restaurant2.getCategoryName(),
                null, 10000,null, null, List.of(menuRequest));

        restaurantService.createRestaurants(List.of(request1, request2));

        // then
        int size = restaurantRepository.findAll().size();
        Assertions.assertThat(size).isEqualTo(2);
    }

    @DisplayName("식당 생성 성공 : 이미 식당이 존재하는 경우 생성 제외")
    @Test
    void createRestaurants_restaurantExist() {
        // given
        Restaurant restaurant = restaurantRepository.save(new Restaurant(category, "한끼식당1", "100", 10000, address));

        // when
        MenuRequest menuRequest = new MenuRequest(true, "돈가스", null, 13000, null);
        RestaurantCreateRequest request1 = new RestaurantCreateRequest(restaurant.getUniqueId(),
                restaurant.getName(),
                restaurant.getCategoryName(),
                null, 10000, null, null, List.of(menuRequest));
        restaurantService.createRestaurants(List.of(request1));

        // then
        int size = restaurantRepository.findAll().size();
        Assertions.assertThat(size).isEqualTo(1);
    }
}
