package com.delivery.yunyun.controller;

import com.delivery.yunyun.domain.DeliveryRider;
import com.delivery.yunyun.dto.request.deliveryRider.CreateDeliveryRiderRequest;
import com.delivery.yunyun.dto.request.deliveryRider.DeliverStatusRequest;
import com.delivery.yunyun.dto.request.deliveryRider.DeliveryRequest;
import com.delivery.yunyun.dto.request.deliveryRider.UpdateDeliveryRiderRequest;
import com.delivery.yunyun.service.DeliveryRiderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/delivery")
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
    @PostMapping("/create")
    private ResponseEntity<Void> deliveryCreate(@RequestBody CreateDeliveryRiderRequest request){
        deliveryRiderService.deliveryCreate(request);
        return ResponseEntity.ok().build();
    }

    // 5. 배달원 수정
    @PutMapping("/update")
    private ResponseEntity<Void> deliveryUpdate(@AuthenticationPrincipal DeliveryRider deliveryRider, UpdateDeliveryRiderRequest request){
        deliveryRiderService.deliveryUpdate(deliveryRider, request);
        return ResponseEntity.ok().build();
    }

    // 6. 배달원 삭제
    @DeleteMapping("/delete")
    private ResponseEntity<Void> deliveryDelete(@AuthenticationPrincipal DeliveryRider deliveryRider){
        deliveryRiderService.deliveryDelete(deliveryRider);
        return ResponseEntity.ok().build();
    }
}
