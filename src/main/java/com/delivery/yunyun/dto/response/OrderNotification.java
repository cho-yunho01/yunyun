package com.delivery.yunyun.dto.response;

import lombok.Builder;

@Builder
public record OrderNotification (
        Long orderId,
        String status
){
}
