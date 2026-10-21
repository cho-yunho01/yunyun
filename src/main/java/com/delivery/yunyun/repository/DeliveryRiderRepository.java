package com.delivery.yunyun.repository;

import com.delivery.yunyun.domain.DeliveryRider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRiderRepository extends JpaRepository<DeliveryRider, Long> {
}
