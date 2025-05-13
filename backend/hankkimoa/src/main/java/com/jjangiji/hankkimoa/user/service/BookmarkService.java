package com.jjangiji.hankkimoa.user.service;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.restaurant.repository.RestaurantRepository;
import com.jjangiji.hankkimoa.user.domain.Bookmark;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.repository.BookmarkRepository;
import com.jjangiji.hankkimoa.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public void addBookmark(Long userId, Long restaurantId) {
        if(bookmarkRepository.existsByUserIdAndRestaurantId(userId, restaurantId)) {
            throw new HankkiMoaException(ExceptionCode.BOOKMARK_EXISTS);
        }
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new HankkiMoaException(ExceptionCode.RESTAURANT_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new HankkiMoaException(ExceptionCode.USER_NOT_FOUND));
        Bookmark bookmark = Bookmark.create(user, restaurant);
        bookmarkRepository.save(bookmark);
    }

    public void removeBookmark(Long userId, Long restaurantId) {
        Bookmark bookmark = bookmarkRepository.findByUserIdAndRestaurantId(userId, restaurantId)
                .orElseThrow(()-> new HankkiMoaException(ExceptionCode.BOOKMARK_NOT_FOUND));
        bookmarkRepository.delete(bookmark);
    }

    public List<Restaurant> getBookmarkedRestaurants(Long userId) {
        return bookmarkRepository.findByUserId(userId).stream()
                .map(Bookmark::getRestaurant)
                .collect(Collectors.toList());
    }
}
