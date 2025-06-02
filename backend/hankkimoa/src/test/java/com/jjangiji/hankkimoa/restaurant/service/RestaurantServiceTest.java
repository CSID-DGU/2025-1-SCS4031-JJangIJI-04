package com.jjangiji.hankkimoa.restaurant.service;

import com.jjangiji.hankkimoa.config.IntegrationTest;
import com.jjangiji.hankkimoa.expense.domain.ExpenseSavingGoal;
import com.jjangiji.hankkimoa.expense.repository.ExpenseSavingGoalRepository;
import com.jjangiji.hankkimoa.restaurant.domain.Address;
import com.jjangiji.hankkimoa.restaurant.domain.Category;
import com.jjangiji.hankkimoa.restaurant.domain.CategoryDictionary;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.repository.CategoryRepository;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import com.jjangiji.hankkimoa.restaurant.service.dto.reqeust.MenuRequest;
import com.jjangiji.hankkimoa.restaurant.service.dto.reqeust.RestaurantCreateRequest;
import com.jjangiji.hankkimoa.restaurant.service.dto.response.RestaurantSimpleResponse;
import com.jjangiji.hankkimoa.restaurant.service.dto.response.RecommendServerRestaurantsResponse;
import com.jjangiji.hankkimoa.restaurant.service.dto.response.RestaurantResponse;
import com.jjangiji.hankkimoa.user.domain.LoginType;
import com.jjangiji.hankkimoa.user.domain.Role;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest extends IntegrationTest {

    @MockitoBean
    private RecommendClient recommendClient;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpenseSavingGoalRepository expenseSavingGoalRepository;

    private Category category;
    private User user;
    private ExpenseSavingGoal expenseSavingGoal;
    private final Address address = new Address(0, 0, "서울 중구 퇴계로18길 20");
    private final LocalDate now = LocalDate.now();
    private final LocalDate sevenDayAfter = now.plusDays(6);

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User("hankkimoa@gmail.com", "한끼", "hankkiImage", LoginType.KAKAO, Role.USER));
        expenseSavingGoal = expenseSavingGoalRepository.save(expenseSavingGoalRepository.save(
                new ExpenseSavingGoal(user, 70_000, now, sevenDayAfter)));
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

    @DisplayName("추천 식당 리스트 조회")
    @Test
    void readRecommendRestaurants() {
        // given
        String uniqueId = "100";
        Restaurant restaurant = restaurantRepository.save(new Restaurant(category, "한끼식당1", uniqueId, 10000, address));

        when(recommendClient.requestRecommendRestaurants(any()))
                .thenReturn(new RecommendServerRestaurantsResponse(List.of(uniqueId)));

        // when
        List<RestaurantSimpleResponse> results = restaurantService.readRecommendRestaurants(user);

        // then
        Assertions.assertThat(results.get(0).id()).isEqualTo(restaurant.getId());
    }

    @DisplayName("식당 조회")
    @Test
    void readRestaurant() {
        // given
        Restaurant restaurant = restaurantRepository.save(new Restaurant(category, "한끼식당1", "100", 10000, address));

        // when
        RestaurantResponse result = restaurantService.readRestaurant(user, restaurant.getId());

        // then
        Assertions.assertThat(result.id()).isEqualTo(restaurant.getId());
    }
}
