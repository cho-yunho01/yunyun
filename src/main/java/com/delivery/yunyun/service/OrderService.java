package com.delivery.yunyun.service;

import com.delivery.yunyun.domain.Customer;
import com.delivery.yunyun.dto.request.order.OrderItemListRequest;
import com.delivery.yunyun.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    public void createOrder(Customer customer, OrderItemListRequest request) {

        /*
        1. 상점 ID 필요
        2. 가게 해당 재고 수량 감소 시켜야하는데 아직 구현 안 함.
        3. 알림을 구현해야함 // WebSocket에 대해 공부
         */

    }
}
