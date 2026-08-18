package com.delivery.yunyun.service;

import com.delivery.yunyun.domain.Cart;
import com.delivery.yunyun.domain.CartItem;
import com.delivery.yunyun.domain.Customer;
import com.delivery.yunyun.domain.Menu;
import com.delivery.yunyun.dto.request.CartItemRequest;
import com.delivery.yunyun.dto.request.ItemAddRequest;
import com.delivery.yunyun.dto.response.CartResponse;
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
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        Menu menu = menuRepository.findById(request.menuId())
                .orElseThrow(() -> new RuntimeException("해당하는 메뉴가 존재하지 않습니다."));

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

    public List<CartResponse> getCart(Long customerId) {
        // 1.사용자 ID를 가지고 해당 카트 객체 필요
        Cart cart = cartRepository.findByCustomer_CustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("해당 사용자가 존재하지 않습니다."));

        // 2. 카트 객체를 가지고 카트 아이템 객체 필요
        List<CartItem> cartItemList = cart.getCartItemList();

        // 3. 카트 아이템 객체를 통해 해당 메뉴 객체를 필요
        return cartItemList.stream().map(
                item -> CartResponse.builder()
                        .cartItemId(item.getCartItemId())
                        .name(item.getMenu().getName())
                        .price(item.getMenu().getPrice())
                        .quantity(item.getQuantity())
                        .build()
        ).toList();


    }

    public void deleteItem(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    public void updateItemQuantity(CartItemRequest request) {
        CartItem cartItem = cartItemRepository.findById(request.cartItemId())
                .orElseThrow();

        cartItem.setQuantity(request.quantity());

        cartItemRepository.save(cartItem);
    }
}
