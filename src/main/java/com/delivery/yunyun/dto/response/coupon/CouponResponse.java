package com.delivery.yunyun.dto.response.coupon;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CouponResponse(
        String couponName,
        BigDecimal discountPrice
) {
}
