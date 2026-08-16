package com.delivery.yunyun.dto.request;


import java.math.BigDecimal;

public record CustomerRequest(
        String name,
        String userId,
        String password,
        BigDecimal balance
) {
}
