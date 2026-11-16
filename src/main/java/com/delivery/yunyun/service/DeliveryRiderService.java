package com.delivery.yunyun.service;

import com.delivery.yunyun.domain.DeliveryRider;
import com.delivery.yunyun.domain.Order;
import com.delivery.yunyun.domain.OrderStatus;
import com.delivery.yunyun.domain.RiderStatus;
import com.delivery.yunyun.dto.request.deliveryRider.CreateDeliveryRiderRequest;
import com.delivery.yunyun.dto.request.deliveryRider.DeliverStatusRequest;
import com.delivery.yunyun.dto.request.deliveryRider.DeliveryRequest;
import com.delivery.yunyun.dto.request.deliveryRider.UpdateDeliveryRiderRequest;
import com.delivery.yunyun.dto.response.deliveryRider.RiderInfoResponse;
import com.delivery.yunyun.error.CustomException;
import com.delivery.yunyun.error.ErrorCode;
import com.delivery.yunyun.repository.DeliveryRiderRepository;
import com.delivery.yunyun.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeliveryRiderService {
    private final DeliveryRiderRepository deliveryRiderRepository;
    private final OrderRepository orderRepository;

    public void deliveryOrderStatus(DeliveryRider rider, DeliveryRequest request) {
        // 조리 완료 후 픽업할 때
        if(request.status().equals("DELIVERING")){
            if(rider.getStatus() != RiderStatus.AVAILABLE){
//            throw new CustomException(); // 배달 가능 상태가 아닐 시 오류 처리 만들기
            }
            else{
                Order order = orderRepository.findById(request.orderId())
                                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
                order.setOrderStatus(OrderStatus.DELIVERING);
                rider.setStatus(RiderStatus.DELIVERING);
                rider.setOrderId(request.orderId());

                orderRepository.save(order);
                deliveryRiderRepository.save(rider);
            }
        }
        // 배달 중에서 배달 완료로 바꿀 때
        else{
            Order order = orderRepository.findById(request.orderId())
                    .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
            order.setOrderStatus(OrderStatus.DELIVERED);
            rider.setStatus(RiderStatus.AVAILABLE);
            rider.setOrderId(null);
            
            orderRepository.save(order);
            deliveryRiderRepository.save(rider);
        }
    }

    public void deliverStatus(DeliveryRider rider, DeliverStatusRequest request) {
        if(request.deliveryStatus().equals("AVAILABLE")){
            rider.setStatus(RiderStatus.AVAILABLE);
        }
        else{
            rider.setStatus(RiderStatus.INACTIVE);
        }
        deliveryRiderRepository.save(rider);
    }

    public void deliveryCreate(CreateDeliveryRiderRequest request) {
        DeliveryRider rider = DeliveryRider.builder()
                .userId(request.userId())
                .password(request.password())
                .name(request.name())
                .phoneNumber(request.phoneNumber())
                .build();
        deliveryRiderRepository.save(rider);
    }

    public void deliveryUpdate(DeliveryRider deliveryRider, UpdateDeliveryRiderRequest request) {
        if(request.password() != null){
            deliveryRider.setPassword(request.password());
        }
        if(request.phoneNumber() != null){
            deliveryRider.setPhoneNumber(request.phoneNumber());
        }

        deliveryRiderRepository.save(deliveryRider);
    }

    public void deliveryDelete(DeliveryRider deliveryRider) {
        deliveryRiderRepository.deleteById(deliveryRider.getId());
    }

    public RiderInfoResponse deliverInfo(DeliveryRider rider) {
        return RiderInfoResponse.builder()
                .userId(rider.getUserId())
                .phoneNumber(rider.getPhoneNumber())
                .status(String.valueOf(rider.getStatus()))
                .build();
    }
}
