package com.delivery.yunyun.controller;

import com.delivery.yunyun.dto.request.MenuRequest;
import com.delivery.yunyun.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuService menuService;
    // 1. 상품 등록
    @PostMapping("/create")
    public ResponseEntity<Void> createMenu(@RequestBody MenuRequest request){
        menuService.createMenu(request);
        return ResponseEntity.ok().build();
    }
    // 2. 상품 삭제
    @DeleteMapping("/delete/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuId){
        menuService.deleteMenu(menuId);
        return ResponseEntity.ok().build();
    }
    // 3. 상품 수정
    @PatchMapping("/update/{menuId}")
    public  ResponseEntity<Void> updateMenu(
            @PathVariable Long menuId,
            @RequestBody MenuRequest request
    ){
        menuService.updateMenu(menuId, request);
        return ResponseEntity.ok().build();
    }

}
