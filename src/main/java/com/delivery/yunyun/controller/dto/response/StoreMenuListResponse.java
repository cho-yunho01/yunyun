package com.delivery.yunyun.controller.dto.response;

public record StoreMenuListResponse(
        String menuName,
        Integer price,
        String introduction
) {
}
