package com.delivery.yunyun.controller;

import com.delivery.yunyun.service.DeliveryRiderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class DeliveryRiderController {
    private final DeliveryRiderService deliveryRiderService;

    // 1. 조리 완료인 배달 음식을 배달 중으로 변환

    // 2. 배달 중인 배달 음식을 배달 완료로 변환
}
