package com.jjangiji.hankkimoa.restaurant.domain;

import com.jjangiji.hankkimoa.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class RestaurantAddress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    private double latitude;

    private double longitude;

    @NotNull(message = "주소가 NULL일 수 없습니다.")
    private String streetAddress;

    public RestaurantAddress(Restaurant restaurant, double latitude, double longitude, String streetAddress) {
        this.restaurant = restaurant;
        this.latitude = latitude;
        this.longitude = longitude;
        this.streetAddress = streetAddress;
    }

    public RestaurantAddress(Long id, Restaurant restaurant, double latitude, double longitude, String streetAddress) {
        this(restaurant, latitude, longitude, streetAddress);
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
        RestaurantAddress that = (RestaurantAddress) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
