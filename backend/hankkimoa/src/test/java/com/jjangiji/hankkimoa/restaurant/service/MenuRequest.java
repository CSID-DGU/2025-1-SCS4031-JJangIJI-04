package com.jjangiji.hankkimoa.restaurant.service;

public record MenuRequest(boolean isMain, String name,
                          String introduce, Integer price, String imgUrl) {
}
