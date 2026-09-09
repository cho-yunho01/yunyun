package com.delivery.yunyun.repository;

import com.delivery.yunyun.domain.Cart;
import com.delivery.yunyun.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCart_CartIdAndMenu_MenuId(Long cartId, Long menuId);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.cart = :cart")
    void deleteAllByCart(Cart cart);
}
