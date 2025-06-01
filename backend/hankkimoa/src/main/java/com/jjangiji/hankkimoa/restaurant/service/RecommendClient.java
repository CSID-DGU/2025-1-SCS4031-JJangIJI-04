package com.jjangiji.hankkimoa.restaurant.service;

import com.jjangiji.hankkimoa.restaurant.service.dto.reqeust.RecommendServerRestaurantsRequest;
import com.jjangiji.hankkimoa.restaurant.service.dto.response.RecommendServerRestaurantsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

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

    public RecommendServerRestaurantsResponse requestRecommendRestaurants(RecommendServerRestaurantsRequest request) {
        return restClient.post()
                .uri(recommendRestaurantsRequestUri)
                .body(request)
                .retrieve()
                .body(RecommendServerRestaurantsResponse.class);
    }
}
