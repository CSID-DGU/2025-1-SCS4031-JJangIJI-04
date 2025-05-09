package com.jjangiji.hankkimoa.restaurant.service.dto;

import java.time.LocalTime;

public record OpeningHoursDetailRequest(LocalTime startTime, LocalTime endTime,
                                        LocalTime breakStartTime, LocalTime breakEndTime,
                                        LocalTime lastOrderTime) {
}
