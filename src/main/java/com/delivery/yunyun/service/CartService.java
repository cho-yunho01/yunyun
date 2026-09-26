package com.delivery.yunyun.service;

import com.delivery.yunyun.domain.Cart;
import com.delivery.yunyun.domain.CartItem;
import com.delivery.yunyun.domain.Customer;
import com.delivery.yunyun.domain.Menu;
import com.delivery.yunyun.dto.request.cart.CartDeleteRequest;
import com.delivery.yunyun.dto.request.cart.CartItemRequest;
import com.delivery.yunyun.dto.request.cart.ItemAddRequest;
import com.delivery.yunyun.dto.response.cart.CartResponse;
import com.delivery.yunyun.error.CustomException;
import com.delivery.yunyun.error.ErrorCode;
import com.delivery.yunyun.repository.CartItemRepository;
import com.delivery.yunyun.repository.CartRepository;
import com.delivery.yunyun.repository.CustomerRepository;
import com.delivery.yunyun.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CustomerRepository customerRepository;
    private final MenuRepository menuRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public void addItem(Customer customer, ItemAddRequest request) {
        Menu menu = menuRepository.findById(request.menuId())
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        Long storeId = menu.getStore().getStoreId();
        Boolean check = checkStore(customer, storeId);

        if(!check){
            throw new CustomException(ErrorCode.CART_STORE_MISMATCH);
        }


        Cart cart = cartRepository.findByCustomer_CustomerId(customer.getCustomerId())
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
                .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

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
                .orElse(null);


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

    public void deleteItem(CartDeleteRequest request) {
        request.cartItemId().forEach(id -> System.out.println("현재 ID의 값: "+id));

        request.cartItemId().forEach(id ->
                cartItemRepository.deleteById(id));
    }

    public void updateItemQuantity(CartItemRequest request) {
        CartItem cartItem = cartItemRepository.findById(request.cartItemId())
                .orElseThrow();

        cartItem.setQuantity(request.quantity());

        cartItemRepository.save(cartItem);
    }

    public Boolean checkStore(Customer customer, Long storeId){
        Optional<Cart> cartOptional = cartRepository.findByCustomer_CustomerId(customer.getCustomerId());

        if (cartOptional.isEmpty()) {
            return true;
        }

        Cart cart = cartOptional.get();

        List<CartItem> cartItemList = cart.getCartItemList();

        return cartItemList.stream()
                .allMatch(
                item -> item.getMenu().getStore().getStoreId().equals(storeId)
        );
    }

    @Transactional
    public void deleteAllItem(Customer customer) {
        System.out.println("Customer의 정보" + customer.getName());
        Cart cart = cartRepository.findByCustomer_CustomerId(customer.getCustomerId())
                .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

        cartItemRepository.deleteAllByCart(cart);

    }
}
