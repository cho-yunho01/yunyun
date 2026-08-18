package com.delivery.yunyun.dto.request;

public record CartItemRequest(
        Long cartItemId,
        Integer quantity
) {
}
