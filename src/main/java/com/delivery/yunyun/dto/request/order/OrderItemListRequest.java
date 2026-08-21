package com.delivery.yunyun.dto.request.order;

import com.delivery.yunyun.domain.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderItemListRequest(
        List<OrderItemRequest> orderItemRequestList,
        Long storeId,
        Long customerId, //@AuthenticationPrincipal로 변환
        OrderStatus orderStatus,
        BigDecimal totalPrice
) {
}
