package com.delivery.yunyun.dto.response;

import lombok.Builder;

@Builder
public record StoreResponse(
        Long storeId,
        String name
) {
}
