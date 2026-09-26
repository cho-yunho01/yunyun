package com.delivery.yunyun.dto.response.order;

import lombok.Builder;

@Builder
public record OrderNotification (
        Long orderId,
        String status
){
}
