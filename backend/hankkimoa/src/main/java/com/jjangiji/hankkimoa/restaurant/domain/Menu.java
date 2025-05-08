package com.jjangiji.hankkimoa.restaurant.domain;

import com.jjangiji.hankkimoa.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "메뉴이름이 NULL일 수 없습니다.")
    private String name;

    private int price;

    private String imageUrl;

    private boolean isMain;

    private String introduce;

    public Menu(String name, int price, String imageUrl, boolean isMain, String introduce) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isMain = isMain;
        this.introduce = introduce;
    }

    public Menu(Long id, String name, int price, String imageUrl, boolean isMain, String introduce) {
        this(name, price, imageUrl, isMain, introduce);
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
        Menu menu = (Menu) object;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
