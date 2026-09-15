package com.delivery.yunyun.repository;

import com.delivery.yunyun.domain.Order;
import com.delivery.yunyun.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStoreId(Long storeId);

    List<Order> findByCustomerId(Long customerId);

    List<Order> findByStoreIdAndOrderStatus(Long storeId, OrderStatus orderStatus);

    List<Order> findByStoreIdAndOrderStatusNot(Long storeId, OrderStatus orderStatus);
}
