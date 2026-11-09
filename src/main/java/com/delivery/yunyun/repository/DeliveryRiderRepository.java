package com.delivery.yunyun.repository;

import com.delivery.yunyun.domain.DeliveryRider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRiderRepository extends JpaRepository<DeliveryRider, Long> {
    Optional<DeliveryRider> findByUserId(String username);
}
