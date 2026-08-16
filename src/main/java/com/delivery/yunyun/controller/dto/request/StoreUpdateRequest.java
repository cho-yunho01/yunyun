package com.delivery.yunyun.controller.dto.request;

import com.delivery.yunyun.domain.Category;

public record StoreUpdateRequest(
        String storeName,
        String storeNumber,
        String address,
        Category category
) {
}
