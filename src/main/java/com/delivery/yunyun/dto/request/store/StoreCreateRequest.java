package com.delivery.yunyun.dto.request.store;

public record StoreCreateRequest (
        Long ownerId,
        String storeName,
        String address,
        String storeNumber
){

}
