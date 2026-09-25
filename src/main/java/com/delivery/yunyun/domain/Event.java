package com.delivery.yunyun.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    private String name;

    private String description;

    private Long couponId;

    private Integer maxCount;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    @OneToMany(mappedBy = "event")
    List<CustomerCoupon> customerCouponList;
}
