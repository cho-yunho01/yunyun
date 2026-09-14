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

    public List<OrderResponse> orderInfoList(Owner owner) {

        Store store = storeRepository.findByOwnerId(owner.getOwnerId());

        List<Order> orderList = orderRepository.findByStoreId(store.getStoreId());

        List<OrderResponse> orderResponses = orderList.stream().map(
                order -> {
                    List<OrderItem> orderItemList = order.getOrderItemList();
//                    for(int i = 0 ;i< orderItemList.size();i++){
//                        System.out.println("orderItemList의 값 ["+i+"] : "+orderItemList.get(i).getOrderItemId());
//                    }
                    List<OrderItemResponse> orderItemResponses = orderItemList.stream().map(
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
                            .orElseThrow( () -> new CustomException(ErrorCode.USER_NOT_FOUND));

//                    for(int i = 0 ;i< orderItemResponses.size(); i++){
//                        System.out.println("orderItemResponses의 값 [" + i + "] : "+orderItemResponses.get(i).menuName());
//                    }

                    return OrderResponse.builder()
                            .orderId(order.getOrderId())
                            .orderItemResponseList(orderItemResponses)
                            .userId(customer.getUserId())
                            .totalPrice(order.getTotalPrice())
                            .build();

                }
        ).toList();

        for(int i = 0 ;i< orderResponses.size(); i++){
            System.out.println("orderResponses의 orderID [" + i + "] : "+orderResponses.get(i).orderId());
        }

        return orderResponses;

    }
}
