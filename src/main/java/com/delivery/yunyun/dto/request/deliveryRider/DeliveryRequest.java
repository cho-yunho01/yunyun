package com.delivery.yunyun.dto.request.deliveryRider;

import lombok.Builder;

@Builder
public record DeliveryRequest(
        Long orderId,
        String status
) {
}
