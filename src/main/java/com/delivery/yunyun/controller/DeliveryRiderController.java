package com.delivery.yunyun.controller;

import com.delivery.yunyun.domain.DeliveryRider;
import com.delivery.yunyun.dto.request.deliveryRider.DeliverStatusRequest;
import com.delivery.yunyun.dto.request.deliveryRider.DeliveryRequest;
import com.delivery.yunyun.service.DeliveryRiderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class DeliveryRiderController {
    private final DeliveryRiderService deliveryRiderService;

    // 1. 주문상태 변화
    public ResponseEntity<?> deliveryOrderStatus(@AuthenticationPrincipal DeliveryRider rider, DeliveryRequest request){
        deliveryRiderService.deliveryOrderStatus(rider, request);
        return ResponseEntity.ok().build();
    }

    // 2. 배달원 상태 변화
    public ResponseEntity<?> deliverStatus(@AuthenticationPrincipal DeliveryRider rider, DeliverStatusRequest request){
        deliveryRiderService.deliverStatus(rider, request);
        return ResponseEntity.ok().build();
    }
    // 3. 배달원 정보 조회


    // 4. 배달원 추가

    // 5. 배달원 수정

    // 6. 배달원 삭제
}
