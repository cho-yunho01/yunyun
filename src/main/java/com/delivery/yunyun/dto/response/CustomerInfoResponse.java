package com.delivery.yunyun.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record CustomerInfoResponse(
        String name,
        String userId,
        BigDecimal balance,
        List<String> roles
) {
}
