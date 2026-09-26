package com.delivery.yunyun.dto.request.coupon;

import java.math.BigDecimal;

public record CouponRequest(
        String couponName,
        BigDecimal discountPrice
) {
}
