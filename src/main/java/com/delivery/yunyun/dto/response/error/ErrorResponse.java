package com.delivery.yunyun.dto.response.error;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String code,
        String message
) {
}
