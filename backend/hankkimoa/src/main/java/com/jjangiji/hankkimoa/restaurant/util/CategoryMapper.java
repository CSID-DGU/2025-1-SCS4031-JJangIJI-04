package com.jjangiji.hankkimoa.restaurant.util;

import com.jjangiji.hankkimoa.restaurant.domain.Category;
import com.jjangiji.hankkimoa.restaurant.domain.CategoryDictionary;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CategoryMapper {

    private final Map<CategoryDictionary, Category> categoryMap;

    public CategoryMapper(List<Category> categories) {
        this.categoryMap = categories.stream()
                .collect(Collectors.toMap(
                        Category::getName, Function.identity()
                ));
    }

    public Category mapByKeyword(String keyword) {
        CategoryDictionary result = CategoryDictionary.findByKeyword(keyword);
        return categoryMap.getOrDefault(result, categoryMap.get(CategoryDictionary.기타));
    }
}
