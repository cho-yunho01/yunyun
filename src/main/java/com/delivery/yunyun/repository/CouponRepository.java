package com.delivery.yunyun.repository;

import com.delivery.yunyun.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Optional<Coupon> findByName(String name);

    Optional<List<Coupon>> findByUserId(Long ownerId);
}
