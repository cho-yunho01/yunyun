package com.delivery.yunyun.dto.request.cart;


public record ItemAddRequest(
        Long menuId,
        Integer quantity
) {
}
