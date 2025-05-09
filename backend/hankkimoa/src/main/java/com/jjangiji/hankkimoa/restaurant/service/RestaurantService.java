package com.jjangiji.hankkimoa.restaurant.service;

import com.jjangiji.hankkimoa.restaurant.domain.Menu;
import com.jjangiji.hankkimoa.restaurant.domain.OpeningHours;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.domain.RestaurantAddress;
import com.jjangiji.hankkimoa.restaurant.domain.RestaurantImage;
import com.jjangiji.hankkimoa.restaurant.repository.MenuRepository;
import com.jjangiji.hankkimoa.restaurant.repository.OpeningHoursRepository;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantAddressRepository;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantImageRepository;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import com.jjangiji.hankkimoa.restaurant.service.dto.RestaurantCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    
    private final RestaurantRepository restaurantRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final RestaurantAddressRepository restaurantAddressRepository;
    private final OpeningHoursRepository openingHoursRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public void createRestaurants(List<RestaurantCreateRequest> requests) {
        restaurantRepository.deleteAll();

        for (RestaurantCreateRequest request : requests) {
            if (request.menus() == null) continue;

            Restaurant restaurant = new Restaurant(null, request.name(), request.id());
            restaurantRepository.save(restaurant);

            saveRestaurantAddress(restaurant, request);
            saveRestaurantImages(restaurant, request);
            saveOpeningHours(restaurant, request);
            saveMenus(restaurant, request);
        }
    }

    private void saveRestaurantImages(Restaurant restaurant, RestaurantCreateRequest request) {
        if (request.images() == null) return;

        List<RestaurantImage> restaurantImages = request.images().stream()
                .map(image -> new RestaurantImage(restaurant, image)).toList();
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

    private void saveRestaurantAddress(Restaurant restaurant, RestaurantCreateRequest request){
        if (request.address() == null) return;

        RestaurantAddress restaurantAddress = new RestaurantAddress(restaurant, 0, 0, request.address());
        restaurantAddressRepository.save(restaurantAddress);
    }
}
