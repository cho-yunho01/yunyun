package com.delivery.yunyun.dto.request.coupon;

import java.math.BigDecimal;

public record UpdateCouponRequest(
        Long couponId,
        String couponName,
        BigDecimal discountPrice
) {
}
