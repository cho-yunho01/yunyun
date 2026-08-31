package com.delivery.yunyun.service;

import com.delivery.yunyun.domain.Menu;
import com.delivery.yunyun.domain.Owner;
import com.delivery.yunyun.domain.Store;
import com.delivery.yunyun.dto.request.MenuRequest;
import com.delivery.yunyun.repository.MenuRepository;
import com.delivery.yunyun.repository.OwnerRepository;
import com.delivery.yunyun.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final OwnerRepository ownerRepository;

    public void createMenu(Owner owner, MenuRequest request) {
        System.out.println(owner.getOwnerId()+"입니다.");
        Store store = storeRepository.findById(owner.getStoreId())
                .orElseThrow(() -> new RuntimeException("해당 상점이 존재하지 않습니다."));
        Menu menu = Menu.builder()
                .name(request.name())
                .price(request.price())
                .introduction(request.introduction())
                .store(store)
                .build();
        menuRepository.save(menu);
    }

    public void deleteMenu(Long menuId) {
        menuRepository.deleteById(menuId);
    }

    public void updateMenu(Long menuId, MenuRequest request) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow();

        if(request.name() != null){
            menu.setName(request.name());
        }

        if(request.price() != null){
            menu.setPrice(request.price());
        }

        if(request.introduction() != null){
            menu.setIntroduction(request.introduction());
        }

        menuRepository.save(menu);
    }
}
