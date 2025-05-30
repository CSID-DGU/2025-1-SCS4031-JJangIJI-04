package com.jjangiji.hankkimoa.restaurant.service;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.restaurant.domain.Address;
import com.jjangiji.hankkimoa.restaurant.domain.Category;
import com.jjangiji.hankkimoa.restaurant.domain.Menu;
import com.jjangiji.hankkimoa.restaurant.domain.OpeningHour;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.domain.RestaurantImage;
import com.jjangiji.hankkimoa.restaurant.repository.CategoryRepository;
import com.jjangiji.hankkimoa.restaurant.repository.MenuRepository;
import com.jjangiji.hankkimoa.restaurant.repository.OpeningHoursRepository;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantImageRepository;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import com.jjangiji.hankkimoa.restaurant.service.dto.response.MenuResponse;
import com.jjangiji.hankkimoa.restaurant.service.dto.response.RecommendRestaurantResponse;
import com.jjangiji.hankkimoa.restaurant.service.dto.reqeust.RestaurantCreateRequest;
import com.jjangiji.hankkimoa.restaurant.service.dto.response.RestaurantResponse;
import com.jjangiji.hankkimoa.restaurant.service.dto.response.RestaurantSearchResponse;
import com.jjangiji.hankkimoa.restaurant.util.CategoryMapper;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    
    private final RestaurantRepository restaurantRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final OpeningHoursRepository openingHoursRepository;
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final BookmarkRepository bookmarkRepository;

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

        List<OpeningHour> openingHours = request.openingHours().stream()
                .map(openingHour -> new OpeningHour(restaurant, openingHour.dayOfWeek(),
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
    public List<RestaurantSearchResponse> searchRestaurants(String keyword) {
        List<Restaurant> restaurants = restaurantRepository.findAllByKeyword(keyword);
        return restaurants.stream()
                .map(restaurant -> new RestaurantSearchResponse(
                        restaurant.getId(),
                        restaurant.getName(),
                        restaurant.getStreetAddress()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RecommendRestaurantResponse> readRecommendRestaurants(User user) {
        List<RecommendRestaurantResponse> result = new ArrayList<>();
        List<Restaurant> recommendRestaurants = restaurantRepository.findAllRecommendRestaurantsByUser(user.getId());

        for (Restaurant restaurant : recommendRestaurants) {
            Optional<OpeningHour> openingHour = readTodayOpeningHour(restaurant);
            Optional<RestaurantImage> restaurantImage = restaurantImageRepository.findByRestaurantId(restaurant.getId());
            boolean bookmared = bookmarkRepository.existsByUserIdAndRestaurantId(user.getId(), restaurant.getId());

            result.add(new RecommendRestaurantResponse(
                    restaurant.getId(),
                    restaurant.getMenuAverage(),
                    restaurantImage.map(RestaurantImage::getImageUrl).orElse(null),
                    restaurant.getStreetAddress(),
                    convertToString(openingHour),
                    restaurant.getCategoryName(),
                    bookmared));
        }

        return result;
    }

    private Optional<OpeningHour> readTodayOpeningHour(Restaurant restaurant) {
        DayOfWeek todayDayOfWeek = LocalDate.now().getDayOfWeek();

        return openingHoursRepository.findAllByRestaurantId(restaurant.getId()).stream()
                .filter(openingHour -> openingHour.isDayOfWeekMatch(todayDayOfWeek))
                .findAny();
    }

    private String convertToString(Optional<OpeningHour> openingHour) {
        return openingHour
                .map(oh -> oh.getDayOfWeek() + " " + oh.getOpenTime() + " - " + oh.getCloseTime())
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public RestaurantResponse readRestaurant(User user, Long restaurantId) {
        Restaurant restaurant = readRestaurant(restaurantId);
        List<String> openingHours = openingHoursRepository.findAllByRestaurantId(restaurant.getId())
                .stream()
                .map(openingHour -> convertToString(Optional.of(openingHour)))
                .toList();
        List<String> restaurantImages = restaurantImageRepository.findAllByRestaurantId(restaurant.getId())
                .stream()
                .map(RestaurantImage::getImageUrl)
                .toList();
        List<MenuResponse> menus = menuRepository.findAllByRestaurantId(restaurant.getId())
                .stream()
                .map(menu -> new MenuResponse(
                        menu.getName(),
                        menu.getIntroduce(),
                        menu.getPrice(),
                        menu.getImageUrl(),
                        menu.isMain()))
                .toList();
        boolean bookmared = bookmarkRepository.existsByUserIdAndRestaurantId(user.getId(), restaurant.getId());

        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getMenuAverage(),
                restaurantImages,
                restaurant.getStreetAddress(),
                openingHours,
                restaurant.getCategoryName(),
                menus,
                bookmared
                );
    }

    private Restaurant readRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new HankkiMoaException(ExceptionCode.RESTAURANT_NOT_FOUND));
    }
}
