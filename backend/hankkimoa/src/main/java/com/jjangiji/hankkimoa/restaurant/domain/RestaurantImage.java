package com.jjangiji.hankkimoa.restaurant.domain;

import com.jjangiji.hankkimoa.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    @Column(length = 1000)
    private String imageUrl;

    public RestaurantImage(Restaurant restaurant, String imageUrl) {
        this.restaurant = restaurant;
        this.imageUrl = imageUrl;
    }

    public RestaurantImage(Long id, Restaurant restaurant, String imageUrl) {
        this(restaurant, imageUrl);
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        RestaurantImage that = (RestaurantImage) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
