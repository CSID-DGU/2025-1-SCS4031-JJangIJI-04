package com.jjangiji.hankkimoa.restaurant.service;

import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.RecommendServerException;
import com.jjangiji.hankkimoa.expense.service.ExpenseSavingGoalService;
import com.jjangiji.hankkimoa.expense.service.dto.response.RemainingBudgetResponse;
import com.jjangiji.hankkimoa.restaurant.service.dto.reqeust.RecommendServerRestaurantsRequest;
import com.jjangiji.hankkimoa.restaurant.service.dto.response.RecommendServerRestaurantsResponse;
import com.jjangiji.hankkimoa.user.domain.User;
import com.jjangiji.hankkimoa.user.domain.UserCategory;
import com.jjangiji.hankkimoa.user.repository.UserCategoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Component
public class RecommendClient {

    private final RestClient restClient;
    private final UserCategoryRepository userCategoryRepository;
    private final ExpenseSavingGoalService expenseSavingGoalService;
    private final String recommendRestaurantsRequestUri;

    public RecommendClient(
            RestClient restClient,
            UserCategoryRepository userCategoryRepository,
            ExpenseSavingGoalService expenseSavingGoalService,
            @Value("${recommend-server.restaurants-post-uri}") String recommendRestaurantsRequestUri) {
        this.restClient = restClient;
        this.userCategoryRepository = userCategoryRepository;
        this.expenseSavingGoalService = expenseSavingGoalService;
        this.recommendRestaurantsRequestUri = recommendRestaurantsRequestUri;
    }

    public RecommendServerRestaurantsResponse requestRecommendRestaurants(User user) {
        RecommendServerRestaurantsRequest recommendRestaurantsRequest = toRecommendServerRestaurantRequest(user);
        return restClient.post()
                .uri(recommendRestaurantsRequestUri)
                .body(recommendRestaurantsRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw new RecommendServerException(getExceptionResponse(res));
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new RecommendServerException(getExceptionResponse(res));
                })
                .body(RecommendServerRestaurantsResponse.class);
    }

    private RecommendServerRestaurantsRequest toRecommendServerRestaurantRequest(User user) {
        List<String> userCategory= userCategoryRepository.findAllByUser(user)
                .stream()
                .map(UserCategory::getCategoryName)
                .toList();
        RemainingBudgetResponse remainingBudgetResponse = expenseSavingGoalService.readRemainingBudget(user, LocalDate.now());
        return new RecommendServerRestaurantsRequest(
                user.getId(),
                remainingBudgetResponse.remainingBudget(),
                userCategory);
    }

    private String getExceptionResponse(ClientHttpResponse response) {
        try {
            return new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new RecommendServerException(ExceptionCode.RECOMMEND_SERVER_INTERNAL_EXCEPTION.getMessage());
        }
    }
}
