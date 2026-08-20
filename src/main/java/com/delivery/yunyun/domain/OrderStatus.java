package com.delivery.yunyun.domain;

public enum OrderStatus {
    PENDING,        // 배송 준비 중
    SHIPPED,        // 배송 시작
    IN_TRANSIT,     // 배송 중
    DELIVERED,      // 배송 완료
    CANCELLED       // 배송 취소
}
