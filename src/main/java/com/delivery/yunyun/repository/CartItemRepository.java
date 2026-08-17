package com.delivery.yunyun.repository;

import com.delivery.yunyun.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCart_CartIdAndMenu_MenuId(Long cartId, Long menuId);
}
