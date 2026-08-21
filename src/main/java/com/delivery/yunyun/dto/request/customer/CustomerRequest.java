package com.delivery.yunyun.dto.request.customer;


import java.math.BigDecimal;

public record CustomerRequest(
        String name,
        String userId,
        String password,
        BigDecimal balance
) {
}
