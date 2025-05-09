package com.jjangiji.hankkimoa.restaurant.service;

import java.time.LocalTime;

public record OpeningHoursDetailRequest(LocalTime startTime, LocalTime endTime,
                                        LocalTime breakStartTime, LocalTime breakEndTime,
                                        LocalTime lastOrderTime) {
}
