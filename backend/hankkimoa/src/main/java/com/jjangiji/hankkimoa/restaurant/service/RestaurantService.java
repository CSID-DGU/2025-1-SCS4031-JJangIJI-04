package com.jjangiji.hankkimoa.restaurant.service;

import com.jjangiji.hankkimoa.restaurant.domain.Address;
import com.jjangiji.hankkimoa.restaurant.domain.Category;
import com.jjangiji.hankkimoa.restaurant.domain.CategoryMapper;
import com.jjangiji.hankkimoa.restaurant.domain.Menu;
import com.jjangiji.hankkimoa.restaurant.domain.OpeningHours;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.domain.RestaurantImage;
import com.jjangiji.hankkimoa.restaurant.repository.CategoryRepository;
import com.jjangiji.hankkimoa.restaurant.repository.MenuRepository;
import com.jjangiji.hankkimoa.restaurant.repository.OpeningHoursRepository;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantImageRepository;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import com.jjangiji.hankkimoa.restaurant.service.dto.RestaurantCreateRequest;
import com.jjangiji.hankkimoa.restaurant.service.dto.RestaurantSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    
    private final RestaurantRepository restaurantRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final OpeningHoursRepository openingHoursRepository;
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createRestaurants(List<RestaurantCreateRequest> requests) {
        Set<String> uniqueIds = restaurantRepository.findAllUniqueId();
        CategoryMapper categoryMapper = new CategoryMapper(categoryRepository.findAll());

        for (RestaurantCreateRequest request : requests) {
            if (request.menus() == null) continue;
            if (request.id() == null || uniqueIds.contains(request.id())) continue;

            Restaurant restaurant = saveRestaurant(request, categoryMapper);
            uniqueIds.add(restaurant.getUniqueId());
            saveRestaurantImages(restaurant, request);
            saveOpeningHours(restaurant, request);
            saveMenus(restaurant, request);
        }
    }

    private Restaurant saveRestaurant(RestaurantCreateRequest request, CategoryMapper categoryMapper) {
        Category category = null;
        Address address = null;
        if (request.category() != null) category = categoryMapper.mapByKeyword(request.category());
        if (request.address() != null) address = new Address(0, 0, request.address());

        Restaurant restaurant = new Restaurant(category, request.name(), request.id(), request.menu_average(), address);
        return restaurantRepository.save(restaurant);
    }

    private void saveRestaurantImages(Restaurant restaurant, RestaurantCreateRequest request) {
        if (request.images() == null) return;

        List<RestaurantImage> restaurantImages = request.images()
                .stream()
                .map(image -> new RestaurantImage(restaurant, image))
                .toList();
        restaurantImageRepository.saveAll(restaurantImages);
    }

    private void saveOpeningHours(Restaurant restaurant, RestaurantCreateRequest request) {
        if (request.openingHours() == null) return;

        List<OpeningHours> openingHours = request.openingHours().stream()
                .map(openingHour -> new OpeningHours(restaurant, openingHour.dayOfWeek(),
                        openingHour.hours().startTime(), openingHour.hours().endTime(),
                        openingHour.hours().breakStartTime(), openingHour.hours().breakEndTime(),
                        openingHour.hours().lastOrderTime()))
                .toList();
        openingHoursRepository.saveAll(openingHours);
    }

    private void saveMenus(Restaurant restaurant, RestaurantCreateRequest request) {
        if (request.menus() == null) return;

        List<Menu> menus = request.menus().stream()
                .map(menu -> new Menu(restaurant, menu.name(),
                        menu.price(), menu.imgUrl(),
                        menu.isMain(), menu.introduce()))
                .toList();
        menuRepository.saveAll(menus);
    }

    @Transactional(readOnly = true)
    public List<RestaurantSearchResponse> readRestaurants(String word) {
        List<Restaurant> restaurants = restaurantRepository.findAllByWord(word);
        return restaurants.stream()
                .map(restaurant -> new RestaurantSearchResponse(
                        restaurant.getId(),
                        restaurant.getName(),
                        restaurant.getStreetAddress()
                ))
                .toList();
    }
}
