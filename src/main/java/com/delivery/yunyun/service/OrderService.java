package com.delivery.yunyun.service;

import com.delivery.yunyun.dto.request.order.OrderItemListRequest;
import com.delivery.yunyun.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    public void createOrder(OrderItemListRequest request) {

    }
}
