package com.jjangiji.hankkimoa.restaurant.service;

import com.jjangiji.hankkimoa.restaurant.service.dto.reqeust.RecommendRestaurantsRequest;
import com.jjangiji.hankkimoa.restaurant.service.dto.response.RecommendRestaurantsResponse;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.domain.UserCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.List;

@RequiredArgsConstructor
@Component
public class RecommendClient {

    @Value("${recommend-server.restaurants-post-uri}")
    private String recommendRestaurantsRequestUri;
    private final RestClient restClient;

    public RecommendRestaurantsResponse requestRecommendRestaurants(User user, List<UserCategory> userCategories) {
        List<String> userCategory = userCategories.stream()
                .map(UserCategory::getCategoryName)
                .toList();
        RecommendRestaurantsRequest request = new RecommendRestaurantsRequest(user.getId(), userCategory);

        return restClient.post()
                .uri(recommendRestaurantsRequestUri)
                .body(request)
                .retrieve()
                .body(RecommendRestaurantsResponse.class);
    }
}
