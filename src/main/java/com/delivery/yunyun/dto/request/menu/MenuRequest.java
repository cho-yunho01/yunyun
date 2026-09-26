package com.delivery.yunyun.dto.request.menu;


import java.math.BigDecimal;

public record MenuRequest(
        String name,
        BigDecimal price,
        String introduction
) {
}
