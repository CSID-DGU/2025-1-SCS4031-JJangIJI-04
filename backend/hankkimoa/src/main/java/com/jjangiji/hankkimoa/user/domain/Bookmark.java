package com.jjangiji.hankkimoa.user.domain;

import com.jjangiji.hankkimoa.common.BaseEntity;
import com.jjangiji.hankkimoa.restaurant.domain.Restaurant;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "bookmarks",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_user_restaurant",
                columnNames = {"user_id","restaurant_id"}
        )
)
public class Bookmark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
    private Restaurant restaurant;

    protected Bookmark(User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
    }

    public static Bookmark create(User user, Restaurant restaurant) {
        return new Bookmark(user, restaurant);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Bookmark bookmark = (Bookmark) object;
        return Objects.equals(id, bookmark.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
