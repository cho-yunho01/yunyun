package com.delivery.yunyun.dto.request.event;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EventRequest(
        String name,
        String description,
        Long couponId,
        Integer maxCount,
        LocalDateTime startAt,
        LocalDateTime endAt
) {
}
