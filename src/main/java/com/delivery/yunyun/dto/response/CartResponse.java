package com.delivery.yunyun.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record CartResponse(
        // 수정이 필요할 시 cartItemId가 필요햠
        Long cartItemId,
        String name,
        BigDecimal price,
        Integer quantity
) {
}
