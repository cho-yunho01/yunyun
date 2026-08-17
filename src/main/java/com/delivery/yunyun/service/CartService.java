package com.delivery.yunyun.service;

import com.delivery.yunyun.domain.Cart;
import com.delivery.yunyun.domain.CartItem;
import com.delivery.yunyun.domain.Customer;
import com.delivery.yunyun.domain.Menu;
import com.delivery.yunyun.dto.request.ItemAddRequest;
import com.delivery.yunyun.repository.CartItemRepository;
import com.delivery.yunyun.repository.CartRepository;
import com.delivery.yunyun.repository.CustomerRepository;
import com.delivery.yunyun.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CustomerRepository customerRepository;
    private final MenuRepository menuRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public void addItem(ItemAddRequest request) {
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow();

        Menu menu = menuRepository.findById(request.menuId())
                .orElseThrow();

        Cart cart = cartRepository.findByCustomer_CustomerId(request.customerId())
                .orElseGet(() -> cartRepository.save(
                        Cart.builder()
                                .customer(customer)
                                .build()
                ));

        CartItem cartItem = cartItemRepository
                .findByCart_CartIdAndMenu_MenuId(cart.getCartId(),request.menuId())
                .orElse(null);

        if(cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + request.quantity());
        }
        else{
            cartItem = CartItem.builder()
                    .cart(cart)
                    .menu(menu)
                    .quantity(request.quantity())
                    .build();
        }

        cartItemRepository.save(cartItem);


    }

    public BigDecimal getTotalPrice(Long customerId) {
        Cart cart = cartRepository.findByCustomer_CustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다."));

        List<CartItem> cartItemList = cart.getCartItemList();

        BigDecimal totalPrice = new BigDecimal(0);

        for(CartItem item : cartItemList){
            BigDecimal price = item.getMenu().getPrice();
            totalPrice = price.multiply(BigDecimal.valueOf(item.getQuantity())).add(totalPrice);

        }

        return totalPrice;
    }
}
