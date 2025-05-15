package com.jjangiji.hankkimoa.user.repository;

import com.jjangiji.hankkimoa.user.domain.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
}
