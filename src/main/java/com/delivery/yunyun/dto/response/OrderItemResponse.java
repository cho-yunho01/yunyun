package com.delivery.yunyun.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemResponse(
         String menuName,
         Integer quantity,
         BigDecimal price
) {
}
