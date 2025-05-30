package com.jjangiji.hankkimoa.restaurant.service.dto.response;

public record MenuResponse(String name,
                           String introduce,
                           Integer price,
                           String imgUrl,
                           boolean main) {
}
