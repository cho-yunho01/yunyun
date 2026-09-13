package com.delivery.yunyun.service;

import com.delivery.yunyun.domain.*;
import com.delivery.yunyun.dto.request.order.OrderItemListRequest;
import com.delivery.yunyun.error.CustomException;
import com.delivery.yunyun.error.ErrorCode;
import com.delivery.yunyun.repository.CartItemRepository;
import com.delivery.yunyun.repository.OrderItemRepository;
import com.delivery.yunyun.repository.OrderRepository;
import com.delivery.yunyun.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final StoreRepository storeRepository;

    public void createOrder(Customer customer, OrderItemListRequest request) {

        Long cartItemId = request.orderItemRequestList().get(0).cartItemId();

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CustomException(ErrorCode.CARTITEM_NOT_FOUND));

        Long storeId = cartItem.getMenu().getStore().getStoreId();

        // Order객체 생성
        Order order = Order.builder()
                .customerId(customer.getCustomerId())
                .storeId(storeId)
                .orderStatus(OrderStatus.PENDING)
                .totalPrice(request.totalPrice())
                .build();

        orderRepository.save(order);

        request.orderItemRequestList().forEach(
                item -> {
                    CartItem  c =  cartItemRepository.findById(item.cartItemId())
                            .orElseThrow(() -> new CustomException(ErrorCode.CARTITEM_NOT_FOUND));

                    Long menuId = c.getMenu().getMenuId();

                    OrderItem orderItem = OrderItem.builder()
                            .order(order)
                            .menuId(menuId)
                            .quantity(c.getQuantity())
                            .build();

                    orderItemRepository.save(orderItem);

                }
        );

        Store store = storeRepository.findById(storeId)
                        .orElseThrow( () -> new CustomException(ErrorCode.STORE_NOT_FOUND));


        simpMessagingTemplate.convertAndSend(
                "/topic/order/"+store.getOwnerId(),
                ""
        );



        /*
        1. 상점 ID 필요
        2. 가게 해당 재고 수량 감소 시켜야하는데 아직 구현 안 함.
        3. 알림을 구현해야함 // WebSocket에 대해 공부


        먼저 Order 객체 생성 후
        orderItem 생성

        삭제시 JPQL 사용하여 삭제 // 고도화 시켜야함
         */

    }
}
