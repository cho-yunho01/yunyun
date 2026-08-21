package com.delivery.yunyun.dto.request.store;

import com.delivery.yunyun.domain.Category;

public record StoreUpdateRequest(
        String storeName,
        String storeNumber,
        String address,
        Category category
) {
}
