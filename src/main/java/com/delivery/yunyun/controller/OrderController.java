package com.delivery.yunyun.controller;

import com.delivery.yunyun.domain.Customer;
import com.delivery.yunyun.domain.Owner;
import com.delivery.yunyun.dto.request.order.OrderItemListRequest;
import com.delivery.yunyun.dto.response.OrderResponse;
import com.delivery.yunyun.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    // 1. 상품 주문 (주문할 상품 ID를 리스트로 받은 다음 사용자 ID와 함께 주문하기)
    @PostMapping("/create")
    public ResponseEntity<Void> createOrder(@AuthenticationPrincipal Customer customer, @RequestBody OrderItemListRequest request){
        orderService.createOrder(customer, request);
        return ResponseEntity.ok().build();
    }

    // 2. 주문 정보
    @GetMapping("/get/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId){
        OrderResponse orderResponse = orderService.getOrder(orderId);
        return ResponseEntity.ok(orderResponse);
    }


    // 3. 주문 수락
    @PatchMapping("/{orderId}/accept")
    public ResponseEntity<Void> acceptOrder(@PathVariable Long orderId){
        orderService.acceptOrder(orderId);
        return ResponseEntity.ok().build();
    }

    // 4. 주문 취소
    @DeleteMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }

    // 5. 가게 주문 정보
    @GetMapping("/info/list")
    public ResponseEntity<List<OrderResponse>> orderInfoList(@AuthenticationPrincipal Owner owner){
        List<OrderResponse> orderResponses =  orderService.orderInfoList(owner);
        return ResponseEntity.ok(orderResponses);
    }



}
