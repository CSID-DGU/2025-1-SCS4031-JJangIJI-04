package com.jjangiji.hankkimoa.restaurant.domain;

import java.util.Arrays;
import java.util.Set;

public enum CategoryDictionary {

    한식(Set.of("육류,고기요리", "곰탕,설렁탕", "포장마차", "요리주점",
            "한식", "순대,순댓국", "감자탕", "칼국수,만두",
            "돼지고기구이", "냉면", "국밥", "주꾸미요리")),
    중식(Set.of("중식당")),
    양식(Set.of("스테이크,립", "스파게티,파스타전문", "양식", "피자",
            "햄버거", "패밀리레스토랑", "와인")),
    일식(Set.of("이자카야", "돈가스", "일식당", "초밥,롤")),
    아시안(Set.of("태국음식", "아시아음식", "베트남음식")),
    분식(Set.of()),
    멕시칸(Set.of("멕시코,남미음식")),
    기타(Set.of("뷔페", "술집")),
    ;

    private final Set<String> keywords;

    CategoryDictionary(Set<String> keywords) {
        this.keywords = keywords;
    }

    public static CategoryDictionary findByKeyword(String keyword) {
        return Arrays.stream(values())
                .filter(value -> value.keywords.contains(keyword))
                .findAny()
                .orElse(기타);
    }
}
