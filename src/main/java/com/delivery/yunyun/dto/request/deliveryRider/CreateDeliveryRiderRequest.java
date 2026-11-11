package com.delivery.yunyun.dto.request.deliveryRider;

public record CreateDeliveryRiderRequest(
        String userId,
        String password,
        String phoneNumber
) {
}
