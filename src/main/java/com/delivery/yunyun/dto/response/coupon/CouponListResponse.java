package com.delivery.yunyun.dto.response.coupon;

import lombok.Builder;

import java.util.List;

@Builder
public record CouponListResponse(
        List<CouponResponse> couponResponse
) {
}
