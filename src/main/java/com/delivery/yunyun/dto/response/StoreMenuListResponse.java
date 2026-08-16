package com.delivery.yunyun.dto.response;

import lombok.Builder;

@Builder
public record StoreMenuListResponse(
        String menuName,
        Integer price,
        String introduction
) {
}
