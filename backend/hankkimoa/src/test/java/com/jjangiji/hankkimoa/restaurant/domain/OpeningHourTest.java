package com.jjangiji.hankkimoa.restaurant.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalTime;

class OpeningHourTest {

    private final Category category = new Category(CategoryDictionary.한식);
    private final Restaurant restaurant = new Restaurant(category, "한끼식당", "110", 15_000, null);

    @DisplayName("영업 요일 확인 성공 : 오늘 요일과 일치하는 경우")
    @Test
    void isDayOfWeekMatch() {
        // given
        OpeningHour openingHour = new OpeningHour(
                restaurant,
                "일요일",
                LocalTime.of(11, 0), LocalTime.of(22, 0),
                LocalTime.of(15, 0), LocalTime.of(17, 0),
                null);

        // when
        DayOfWeek dayOfWeek = DayOfWeek.SUNDAY;
        boolean result = openingHour.isDayOfWeekMatch(dayOfWeek);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("영업 요일 확인 성공 : 오늘 요일과 일치하지 않는 경우")
    @Test
    void isDayOfWeekNotMatch() {
        // given
        OpeningHour openingHour = new OpeningHour(
                restaurant,
                "월요일",
                LocalTime.of(11, 0), LocalTime.of(22, 0),
                LocalTime.of(15, 0), LocalTime.of(17, 0),
                null);

        // when
        DayOfWeek dayOfWeek = DayOfWeek.SUNDAY;
        boolean result = openingHour.isDayOfWeekMatch(dayOfWeek);

        // then
        Assertions.assertThat(result).isFalse();
    }
}
