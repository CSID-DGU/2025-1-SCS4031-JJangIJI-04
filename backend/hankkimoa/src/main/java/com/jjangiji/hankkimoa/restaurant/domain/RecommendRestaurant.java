package com.jjangiji.hankkimoa.restaurant.domain;

import com.jjangiji.hankkimoa.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class RecommendRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "유저는 NULL일 수 없습니다.")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @NotNull(message = "식당은 NULL일 수 없습니다.")
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    public RecommendRestaurant(User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
    }

    public RecommendRestaurant(Long id, User user, Restaurant restaurant) {
        this(user, restaurant);
        this.id = id;
    }
}
