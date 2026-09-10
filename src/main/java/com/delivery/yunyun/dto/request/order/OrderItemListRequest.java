package com.delivery.yunyun.dto.request.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderItemListRequest(
        List<OrderItemRequest> orderItemRequestList,
        BigDecimal totalPrice
) {
}
