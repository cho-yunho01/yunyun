package com.delivery.yunyun.dto.request.customer;

import java.math.BigDecimal;

public record UpdateCustomerRequest(
        String password,
        BigDecimal balance
) {
}
