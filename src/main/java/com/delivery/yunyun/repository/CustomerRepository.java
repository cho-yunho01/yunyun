package com.delivery.yunyun.repository;

import com.delivery.yunyun.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
