package com.delivery.yunyun.dto.request;

public record StoreCreateRequest (
        Long ownerId,
        String storeName,
        String address,
        String storeNumber
){

}
