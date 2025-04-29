package com.jjangiji.hankkimoa.member.domain;

import lombok.Getter;

@Getter
public enum Category {
    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    WESTERN("양식"),
    ASIAN("아시아음식"),
    MEXICAN("멕시칸"),
    STREETFOOD("분식"),
    ETC("기타");

    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
