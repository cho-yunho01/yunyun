package com.delivery.yunyun.dto.response.deliveryRider;

import lombok.Builder;

@Builder
public record RiderInfoResponse(
        String userId,
        String phoneNumber,
        String status
) {
}
