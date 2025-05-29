package com.jjangiji.hankkimoa.restaurant.domain;

import com.jjangiji.hankkimoa.restaurant.util.CategoryMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

class CategoryMapperTest {

    private final Category category1 = new Category(1, CategoryDictionary.한식);
    private final Category category2 = new Category(2, CategoryDictionary.기타);
    private final CategoryMapper categoryMapper = new CategoryMapper(List.of(category1, category2));


    @DisplayName("카테고리 매핑 성공")
    @Test
    void mapByCategory() {
        // given & when
        Category result = categoryMapper.mapByKeyword("냉면");

        // then
        Assertions.assertThat(result).isEqualTo(category1);
    }

    @DisplayName("카테고리 매핑 성공 : 카테고리 존재하지 않으면 기타 반환")
    @Test
    void map기타() {
        // given & when
        Category result = categoryMapper.mapByKeyword("abc");

        // then
        Assertions.assertThat(result).isEqualTo(category2);
    }
}
