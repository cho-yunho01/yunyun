package com.delivery.yunyun.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record StoreMenuListResponse(
        String menuName,
        BigDecimal price,
        String introduction
) {
}
