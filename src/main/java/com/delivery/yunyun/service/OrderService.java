package com.delivery.yunyun.service;

import com.delivery.yunyun.domain.*;
import com.delivery.yunyun.dto.request.order.OrderItemListRequest;
import com.delivery.yunyun.dto.response.OrderItemResponse;
import com.delivery.yunyun.dto.response.OrderNotification;
import com.delivery.yunyun.dto.response.OrderResponse;
import com.delivery.yunyun.error.CustomException;
import com.delivery.yunyun.error.ErrorCode;
import com.delivery.yunyun.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final CustomerRepository customerRepository;

    public void createOrder(Customer customer, OrderItemListRequest request) {
        System.out.println("OrderItemListRequest의 값: "+request);

        Long cartItemId = request.orderItemRequestList().get(0).cartItemId();

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CustomException(ErrorCode.CARTITEM_NOT_FOUND));

        Long storeId = cartItem.getMenu().getStore().getStoreId();

        // Order 객체 생성
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
                "/topic/owner/order/"+store.getOwnerId(),
                order.getOrderId()
        );

//        simpMessagingTemplate.convertAndSend(
//                "/topic/order/"+1,
//                "전송 성공"
//        );


        /*
        1. 상점 ID 필요
        2. 가게 해당 재고 수량 감소 시켜야하는데 아직 구현 안 함.
        3. 알림을 구현해야함 // WebSocket에 대해 공부


        먼저 Order 객체 생성 후
        orderItem 생성

        삭제시 JPQL 사용하여 삭제 // 고도화 시켜야함
         */

    }

    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        List<OrderItem> orderItemList = order.getOrderItemList();

        List<OrderItemResponse> orderItemResponseList = orderItemList.stream().map(
                item -> {
                    Menu menu = menuRepository.findById(item.getMenuId())
                            .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

                    return OrderItemResponse.builder()
                            .menuName(menu.getName())
                            .quantity(item.getQuantity())
                            .price(menu.getPrice())
                            .build();

                }
        ).toList();

        Customer customer = customerRepository.findById(order.getCustomerId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return OrderResponse.builder()
                .orderId(orderId)
                .orderItemResponseList(orderItemResponseList)
                .userId(customer.getUserId())
                .totalPrice(order.getTotalPrice())
                .build();
    }

    public void acceptOrder(Long orderId) {
        System.out.println("acceptOrder 진입 성공");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

//        order.setOrderStatus(OrderStatus.COOKING);

        Long customerId = order.getCustomerId();

        orderRepository.save(order);

        simpMessagingTemplate.convertAndSend(
                "/topic/customer/order/"+customerId,
                OrderNotification.builder()
                        .orderId(orderId)
                        .status("ACCEPT")
                        .build()
        );

    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        orderRepository.delete(order);

        Long customerId = order.getCustomerId();

        simpMessagingTemplate.convertAndSend(
                "/topic/customer/order/"+customerId,
                OrderNotification.builder()
                        .orderId(orderId)
                        .status("CANCEL")
                        .build()
        );
    }
}
