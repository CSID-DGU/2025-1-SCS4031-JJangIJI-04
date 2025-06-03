package com.jjangiji.hankkimoa.restaurant.repository;

import com.jjangiji.hankkimoa.restaurant.domain.RecommendationFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationFeedbackRepository extends JpaRepository<RecommendationFeedback, Long> {
}
