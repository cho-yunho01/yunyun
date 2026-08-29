package com.delivery.yunyun.repository;

import com.delivery.yunyun.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Long findByStoreId(Long ownerId);

    Optional<Owner> findByUserId(String username);
}
