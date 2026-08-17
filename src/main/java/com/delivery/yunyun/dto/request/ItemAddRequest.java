package com.delivery.yunyun.dto.request;


public record ItemAddRequest(
        Long customerId,
        Long menuId,
        Integer quantity
) {
}
