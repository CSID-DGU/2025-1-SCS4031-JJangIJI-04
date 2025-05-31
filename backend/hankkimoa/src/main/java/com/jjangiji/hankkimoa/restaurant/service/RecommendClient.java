package com.jjangiji.hankkimoa.restaurant.service;

import com.jjangiji.hankkimoa.restaurant.service.dto.reqeust.RecommendServerRestaurantsRequest;
import com.jjangiji.hankkimoa.restaurant.service.dto.response.RecommendServerRestaurantsResponse;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.domain.UserCategory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.List;

@Component
public class RecommendClient {

    private final RestClient restClient;
    private final String recommendRestaurantsRequestUri;

    public RecommendClient(
            RestClient restClient,
            @Value("${recommend-server.restaurants-post-uri}") String recommendRestaurantsRequestUri) {
        this.restClient = restClient;
        this.recommendRestaurantsRequestUri = recommendRestaurantsRequestUri;
    }

    public RecommendServerRestaurantsResponse requestRecommendRestaurants(User user, List<UserCategory> userCategories) {
        List<String> userCategory = userCategories.stream()
                .map(UserCategory::getCategoryName)
                .toList();
        RecommendServerRestaurantsRequest request = new RecommendServerRestaurantsRequest(user.getId(), userCategory);

        return restClient.post()
                .uri(recommendRestaurantsRequestUri)
                .body(request)
                .retrieve()
                .body(RecommendServerRestaurantsResponse.class);
    }
}
