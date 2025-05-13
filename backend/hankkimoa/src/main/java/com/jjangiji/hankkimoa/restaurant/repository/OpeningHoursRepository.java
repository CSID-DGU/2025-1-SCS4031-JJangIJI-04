package com.jjangiji.hankkimoa.restaurant.repository;

import com.jjangiji.hankkimoa.restaurant.domain.OpeningHours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpeningHoursRepository extends JpaRepository<OpeningHours, Long> {
}
