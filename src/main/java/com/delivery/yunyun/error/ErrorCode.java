package com.delivery.yunyun.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 사용자
    USER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "USER_001",
            "사용자를 찾을 수 없습니다."
    ),

    INVALID_PASSWORD(
            HttpStatus.UNAUTHORIZED,
            "USER_002",
            "비밀번호가 올바르지 않습니다."
    ),

    DUPLICATE_USER_ID(
            HttpStatus.CONFLICT,
            "USER_003",
            "이미 사용 중인 아이디입니다."
    ),

    // 상점
    STORE_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "STORE_001",
                    "상점을 찾을 수 없습니다."
    ),

    // 장바구니 아이템
    CART_STORE_MISMATCH(
            HttpStatus.CONFLICT,
            "cartItem_001",
            "같은 매장의 상품만 담을 수 있습니다."
    ),

    // 장바구니
    CART_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "CART_O01",
            "장바구니를 찾을 수 없습니다."
    ),

    // 장바구니 아이템
    CARTITEM_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "CARTITEM_001",
            "장바구니 메뉴를 찾을 수 없습니다."
    ),

    // 메뉴

    MENU_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "MENU_001",
                    "해당 메뉴를 찾을 수 없습니다."
    ),


    // 주문

    ORDER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "ORDER_001",
            "해당 주문 정보를 찾을 수 없습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
