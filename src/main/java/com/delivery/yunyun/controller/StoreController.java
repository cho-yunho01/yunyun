package com.delivery.yunyun.controller;

import com.delivery.yunyun.dto.request.StoreCreateRequest;
import com.delivery.yunyun.dto.request.StoreUpdateRequest;
import com.delivery.yunyun.dto.response.StoreMenuListResponse;
import com.delivery.yunyun.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    // 1. 점주 id 입력받아 가게 등록하기 (유저 아이디)
    @PostMapping("/stores")
    public ResponseEntity<Void> createStore(@RequestBody StoreCreateRequest request){
        storeService.createStore(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    // 2. 해당 가게 메뉴 전체 보여주기
    @GetMapping("/stores/{storeId}/menus")
    public ResponseEntity<List<StoreMenuListResponse>> getMenus(@PathVariable Long storeId){
        List<StoreMenuListResponse> menus = storeService.getMenus(storeId);
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
}
