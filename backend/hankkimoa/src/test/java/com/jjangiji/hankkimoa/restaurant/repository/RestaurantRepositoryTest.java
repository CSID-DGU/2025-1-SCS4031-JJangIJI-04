package com.jjangiji.hankkimoa.restaurant.repository;

import com.jjangiji.hankkimoa.config.RepositoryTest;
import com.jjangiji.hankkimoa.restaurant.domain.Address;
import com.jjangiji.hankkimoa.restaurant.domain.Category;
import com.jjangiji.hankkimoa.restaurant.domain.CategoryDictionary;
import com.jjangiji.hankkimoa.restaurant.domain.RecommendRestaurant;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.user.domain.LoginType;
import com.jjangiji.hankkimoa.user.domain.Role;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

class RestaurantRepositoryTest extends RepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecommendRestaurantRepository recommendRestaurantRepository;

    private User user;
    private Category category;
    private Address address = new Address(0, 0, "서울 중구 퇴계로18길 20");

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User("hankkimoa@gmail.com", "한끼", "hankkiImage", LoginType.KAKAO, Role.USER));
        category = categoryRepository.save(new Category(CategoryDictionary.한식));
    }

    @DisplayName("사용자 추천 식당 조회 성공")
    @Test
    void findAllRecommendRestaurantsByUser() {
        // given
        Restaurant restaurant1 = new Restaurant(category, "한끼식당1", "100", 10000, address);
        Restaurant restaurant2 = new Restaurant(category, "한끼식당2", "110", 10000, address);
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2));

        RecommendRestaurant recommendRestaurant = new RecommendRestaurant(user, restaurant1);
        recommendRestaurantRepository.save(recommendRestaurant);

        // when
        List<Restaurant> result = restaurantRepository.findAllRecommendRestaurantsByUser(user.getId());

        // then
        Assertions.assertThat(result).containsOnly(restaurant1);
    }
}
