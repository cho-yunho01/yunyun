package com.delivery.yunyun.dto.request;


import java.math.BigDecimal;

public record MenuRequest(
        String name,
        BigDecimal price,
        String introduction,
        Long storeId
) {
}
