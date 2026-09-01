package com.delivery.yunyun.controller;

import com.delivery.yunyun.domain.Owner;
import com.delivery.yunyun.dto.request.store.StoreCreateRequest;
import com.delivery.yunyun.dto.request.store.StoreUpdateRequest;
import com.delivery.yunyun.dto.response.StoreMenuListResponse;
import com.delivery.yunyun.dto.response.StoreResponse;
import com.delivery.yunyun.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    // 1. 점주 id 입력받아 가게 등록하기 (유저 아이디)
    @PostMapping("/stores")
    public ResponseEntity<Void> createStore(@AuthenticationPrincipal Owner owner, @RequestBody StoreCreateRequest request){
        storeService.createStore(owner, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    // 2.1. 해당 가게 메뉴 전체 보여주기 (관리자)
    @GetMapping("/stores/menus")
    public ResponseEntity<List<StoreMenuListResponse>> getMenus(@AuthenticationPrincipal Owner owner){
        List<StoreMenuListResponse> menus = storeService.getMenus(owner);
        return ResponseEntity.ok(menus);
    }
    // 2.2. 해당 가게 메뉴 전체 보여주기 (사용자)
    @GetMapping("/stores/menus/{storeId}")
    public ResponseEntity<List<StoreMenuListResponse>> getMenusByCustomer(@PathVariable Long storeId){
        List<StoreMenuListResponse> menus = storeService.getMenusByCustomer(storeId);
        return ResponseEntity.ok(menus);
    }

    // 3. 조건 검색 기능 구현
    @GetMapping("/stores/{storeId}/menus/{menuName}")
    public ResponseEntity<StoreMenuListResponse> getMenu(@PathVariable Long storeId, @PathVariable String menuName){
        StoreMenuListResponse menu = storeService.getMenu(storeId, menuName);
        return ResponseEntity.ok(menu);
    }
    // 4. 가게 정보 수정
    @PutMapping("/stores/{storeId}")
    public ResponseEntity<Void> updateStore(
            @PathVariable Long storeId,
            @RequestBody StoreUpdateRequest request
            ){
        storeService.updateStore(storeId,request);
        return ResponseEntity.ok().build();
    }
    // 5. 가게 삭제
    @DeleteMapping("/stores/{storeId}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long storeId){
        storeService.deleteStore(storeId);
        return ResponseEntity.ok().build();
    }
    
    // 6. 가게 검색
    @GetMapping("/find/stores/{storeName}")
    public ResponseEntity<List<StoreResponse>> findStore(@PathVariable String storeName ){
        List<StoreResponse> storeResponse = storeService.findStore(storeName);
        return ResponseEntity.ok(storeResponse);
    }

    // 7. 모든 가게검색 (규모가 적을 경우 그냥 한 번에 많은 가게 탐색)
    @GetMapping("/find/stores")
    public ResponseEntity<List<StoreResponse>> findStores(){
        List<StoreResponse> storeResponses = storeService.findStores();
        return ResponseEntity.ok(storeResponses);
    }
}
