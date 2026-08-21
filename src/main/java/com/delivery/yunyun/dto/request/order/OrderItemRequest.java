package com.delivery.yunyun.dto.request.order;

public record OrderItemRequest(
        Long menuId,
        Integer quantity
) {
}
