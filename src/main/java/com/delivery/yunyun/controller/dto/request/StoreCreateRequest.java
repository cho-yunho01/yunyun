package com.delivery.yunyun.controller.dto.request;

public record StoreCreateRequest (
        Long ownerId,
        String storeName,
        String address,
        String storeNumber
){

}
