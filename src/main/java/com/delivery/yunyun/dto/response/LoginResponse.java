package com.delivery.yunyun.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        Long id,
        String token
) {
}
