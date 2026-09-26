package com.delivery.yunyun.dto.response.store;

import lombok.Builder;

@Builder
public record StoreResponse(
        Long storeId,
        String name
) {
}
