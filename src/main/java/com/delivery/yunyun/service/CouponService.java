package com.delivery.yunyun.service;

import com.delivery.yunyun.domain.Coupon;
import com.delivery.yunyun.domain.Owner;
import com.delivery.yunyun.dto.request.coupon.CouponRequest;
import com.delivery.yunyun.dto.response.coupon.CouponResponse;
import com.delivery.yunyun.error.CustomException;
import com.delivery.yunyun.error.ErrorCode;
import com.delivery.yunyun.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;

    public void addCoupon(Owner owner, CouponRequest request) {
        Coupon coupon = Coupon.builder()
                .name(request.couponName())
                .discountPrice(request.discountPrice())
                .userId(owner.getOwnerId())
                .build();

        couponRepository.save(coupon);
    }

    public void updateCoupon(Owner owner, CouponRequest request) {
        Coupon coupon = couponRepository.findByName(request.couponName())
                .orElseThrow(() -> new CustomException(ErrorCode.COUPON_NOT_FOUND));

        if(request.couponName() != null){
            coupon.setName(request.couponName());
        }

        if(request.discountPrice() != null){
            coupon.setDiscountPrice(request.discountPrice());
        }

        couponRepository.save(coupon);
    }

    public List<CouponResponse> getCoupon(Owner owner) {
        List<Coupon> coupons = couponRepository.findByUserId(owner.getOwnerId())
                .orElseThrow(() -> new CustomException(ErrorCode.COUPON_NOT_FOUND));

        List<CouponResponse> couponResponses = coupons.stream()
                .map(coupon -> {
                    CouponResponse couponResponse = CouponResponse.builder()
                            .couponName(coupon.getName())
                            .discountPrice(coupon.getDiscountPrice())
                            .build();
                    return couponResponse;
                })
                .toList();

        return couponResponses;

    }
}
