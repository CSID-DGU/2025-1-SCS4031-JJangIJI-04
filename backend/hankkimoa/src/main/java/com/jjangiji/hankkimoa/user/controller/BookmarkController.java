package com.jjangiji.hankkimoa.user.controller;

import com.jjangiji.hankkimoa.auth.config.AuthRequiredPrincipal;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/restaurants/{restaurantId}")
    public ResponseEntity<String> addBookmark(@AuthRequiredPrincipal User user, @PathVariable Long restaurantId) {
        bookmarkService.addBookmark(user.getId(), restaurantId);
        return ResponseEntity.ok("즐겨찾기에 추가되었습니다.");
    }

    @DeleteMapping("/restaurants/{restaurantId}")
    public ResponseEntity<String> removeBookmark( @AuthRequiredPrincipal User user, @PathVariable Long restaurantId) {
        bookmarkService.removeBookmark(user.getId(), restaurantId);
        return ResponseEntity.ok("즐겨찾기가 삭제되었습니다.");
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getBookmarkedRestaurants(@AuthRequiredPrincipal User user) {
        List<Restaurant> bookmarkedRestaurants = bookmarkService.getBookmarkedRestaurants(user.getId());
        return ResponseEntity.ok(bookmarkedRestaurants);
    }
}
