package com.delivery.yunyun.dto.response.event;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record EventResponse(
        String name,
        String description,
        Long couponId,
        Integer maxCount,
        LocalDateTime startAt,
        LocalDateTime endAt
) {
}
