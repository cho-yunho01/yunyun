package com.delivery.yunyun.repository;

import com.delivery.yunyun.domain.CustomerCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCouponRepository extends JpaRepository<CustomerCoupon, Long> {
}
