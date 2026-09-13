package com.delivery.yunyun.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderResponse(
        List<OrderItemResponse> orderItemResponseList,
        String userId,
        BigDecimal totalPrice
) {
}
