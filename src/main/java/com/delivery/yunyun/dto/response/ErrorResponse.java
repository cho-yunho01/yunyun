package com.delivery.yunyun.dto.response;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String code,
        String message
) {
}
