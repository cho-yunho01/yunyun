package com.delivery.yunyun.dto.request.cart;

public record CartItemRequest(
        Long cartItemId,
        Integer quantity
) {
}
