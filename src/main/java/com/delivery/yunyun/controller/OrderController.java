package com.delivery.yunyun.controller;

import com.delivery.yunyun.dto.request.order.OrderItemListRequest;
import com.delivery.yunyun.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    // 1. 상품 주문 (주문할 상품 ID를 리스트로 받은 다음 사용자 ID와 함께 주문하기)
    @PostMapping("/create")
    public ResponseEntity<Void> createOrder(@RequestBody OrderItemListRequest request){
        orderService.createOrder(request);
        return ResponseEntity.ok().build();
    }
    // 2. 주문 취소 (주문 ID를 가지고 주문 취소하기)
    public void cancleOrder(){

    }
    // 3. 주문 받기 (점주가 사용자로부터 주문을 받아서 수락 or 거절)
    public void processOrder(){

    }
}
