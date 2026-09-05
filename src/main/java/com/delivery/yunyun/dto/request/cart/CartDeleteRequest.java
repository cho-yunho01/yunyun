package com.delivery.yunyun.dto.request.cart;

import java.util.List;

public record CartDeleteRequest(
        List<Long> cartItemId
) {
}
