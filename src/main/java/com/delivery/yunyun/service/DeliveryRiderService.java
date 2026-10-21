package com.delivery.yunyun.service;

import com.delivery.yunyun.repository.DeliveryRiderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeliveryRiderService {
    private final DeliveryRiderRepository deliveryRiderRepository;
}
