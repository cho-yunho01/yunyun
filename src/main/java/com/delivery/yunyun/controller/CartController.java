package com.delivery.yunyun.controller;

import com.delivery.yunyun.dto.request.CartItemRequest;
import com.delivery.yunyun.dto.request.ItemAddRequest;
import com.delivery.yunyun.dto.response.CartResponse;
import com.delivery.yunyun.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    // 1. 상품 담기
    @PostMapping("/add")
    public ResponseEntity<Void> addItem(@RequestBody ItemAddRequest request) {
        cartService.addItem(request);
        return ResponseEntity.ok().build();
    }

    // 2. 카트 총 가격
    @GetMapping("/total/{customerId}")
    public ResponseEntity<BigDecimal> getTotalPrice(@PathVariable Long customerId) {
        BigDecimal totalPrice = cartService.getTotalPrice(customerId);
        return ResponseEntity.ok(totalPrice);
    }

    // 3. 장바구니 조회
    @GetMapping("/getCart/{customerId}")
    public ResponseEntity<List<CartResponse>> getCart(@PathVariable Long customerId) {
        List<CartResponse> menuList = cartService.getCart(customerId);
        return ResponseEntity.ok(menuList);
    }

    // 4. 장바구니 상품 삭제
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long cartItemId) {
        cartService.deleteItem(cartItemId);
        return ResponseEntity.ok().build();
    }

    // 5. 장바구니 상품 수정 (수량)
    @PutMapping("/update/quantity")
    public ResponseEntity<Void> updateItemQuantity(@RequestBody CartItemRequest request) {
        cartService.updateItemQuantity(request);
        return ResponseEntity.ok().build();
    }
}
