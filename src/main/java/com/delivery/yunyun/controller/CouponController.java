package com.delivery.yunyun.controller;

import com.delivery.yunyun.domain.Owner;
import com.delivery.yunyun.dto.request.coupon.CouponRequest;
import com.delivery.yunyun.dto.response.coupon.CouponListResponse;
import com.delivery.yunyun.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon")
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/add")
    public ResponseEntity<?> addCoupone(@AuthenticationPrincipal Owner owner, CouponRequest request){
        couponService.addCoupon(owner, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCoupone(@AuthenticationPrincipal Owner owner, CouponRequest request){
        couponService.updateCoupon(owner, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<CouponListResponse> getCoupon(@AuthenticationPrincipal Owner owner){
        CouponListResponse response = couponService.getCoupon(owner);
        return ResponseEntity.ok(response);
    }

    // 갑자기 쿠폰을 삭제하면 문제가 발생할 수 있으므로 잠시 보류
    public ResponseEntity<?> deleteCoupon(@AuthenticationPrincipal Owner owner, @PathVariable Long couponId){
//        couponService.deleteCoupon(owner, couponId);
        return ResponseEntity.ok().build();
    }


}
