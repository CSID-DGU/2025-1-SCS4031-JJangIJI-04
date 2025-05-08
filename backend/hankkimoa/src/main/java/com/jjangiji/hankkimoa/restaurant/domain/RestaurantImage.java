package com.jjangiji.hankkimoa.restaurant.domain;

import com.jjangiji.hankkimoa.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class RestaurantImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    private String imageUrl;

    public RestaurantImage(Restaurant restaurant, String imageUrl) {
        this.restaurant = restaurant;
        this.imageUrl = imageUrl;
    }

    public RestaurantImage(Long id, Restaurant restaurant, String imageUrl) {
        this(restaurant, imageUrl);
        this.id = id;
    }
}
