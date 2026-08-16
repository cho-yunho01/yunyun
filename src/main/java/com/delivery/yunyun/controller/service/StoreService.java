package com.delivery.yunyun.controller.service;

import com.delivery.yunyun.controller.dto.request.StoreCreateRequest;
import com.delivery.yunyun.controller.dto.request.StoreUpdateRequest;
import com.delivery.yunyun.controller.dto.response.StoreMenuListResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    public void createStore(StoreCreateRequest request) {
    }

    public List<StoreMenuListResponse> getMenus(Long storeId) {
    }

    public StoreMenuListResponse getMenu(Long storeId, Long menuId) {
    }

    public void updateStore(Long storeId, StoreUpdateRequest request) {
    }

    public void deleteStore(Long storeId) {
    }
}
