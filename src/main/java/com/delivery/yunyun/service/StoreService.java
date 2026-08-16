package com.delivery.yunyun.service;

import com.delivery.yunyun.domain.Owner;
import com.delivery.yunyun.dto.request.StoreCreateRequest;
import com.delivery.yunyun.dto.request.StoreUpdateRequest;
import com.delivery.yunyun.dto.response.StoreMenuListResponse;
import com.delivery.yunyun.repository.MenuRepository;
import com.delivery.yunyun.repository.OwnerRepository;
import com.delivery.yunyun.repository.StoreRepository;
import com.delivery.yunyun.domain.Menu;
import com.delivery.yunyun.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    public void createStore(StoreCreateRequest request) {

        Store store = Store.builder()
                .storeName(request.storeName())
                .storeNumber(request.storeNumber())
                .address(request.address())
                .ownerId(request.ownerId())
                .build();
        storeRepository.save(store);
    }

    public List<StoreMenuListResponse> getMenus(Long storeId) {
        List<Menu> menuList = menuRepository.findAllByStore_StoreId(storeId);
        return menuList.stream().map(
                (menu ->
                        StoreMenuListResponse.builder()
                                .menuName(menu.getName())
                                .price(menu.getPrice())
                                .introduction(menu.getIntroduction())
                                .build())
        ).toList();
    }

    public StoreMenuListResponse getMenu(Long storeId, String menuName) {
        Menu menu = menuRepository.findAllByStore_StoreIdAndName(storeId,menuName);
        return StoreMenuListResponse.builder()
                .menuName(menu.getName())
                .price(menu.getPrice())
                .introduction(menu.getIntroduction())
                .build();
    }

    public void updateStore(Long storeId, StoreUpdateRequest request) {
        Store store = storeRepository.findById(storeId).orElseThrow();
        if(request.storeName() != null){
            store.setStoreName(request.storeName());
        }

        if(request.storeNumber() != null){
            store.setStoreNumber(request.storeNumber());
        }

        if(request.address() != null){
            store.setAddress(request.address());
        }

        if(request.category() != null){
            store.setCategory(request.category());
        }

        storeRepository.save(store);
    }

    public void deleteStore(Long storeId) {
        storeRepository.deleteById(storeId);
    }
}
