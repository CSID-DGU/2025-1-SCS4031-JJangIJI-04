package com.jjangiji.hankkimoa.restaurant.repository;

import com.jjangiji.hankkimoa.restaurant.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
