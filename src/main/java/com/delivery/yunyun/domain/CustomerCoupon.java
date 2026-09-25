package com.delivery.yunyun.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerCouponId;

    private Long customerId;

    private Long couponId;

    @ManyToOne
    private Event event;

    private LocalDateTime issuedAt; // 발급된 시간

    private LocalDateTime usedAt;
}
