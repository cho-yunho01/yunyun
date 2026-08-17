package com.delivery.yunyun.controller;

import com.delivery.yunyun.dto.request.OwnerRequest;
import com.delivery.yunyun.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;
    // 1. 사용자 추가
    @PostMapping("/create")
    private ResponseEntity<Void> ownerCreate(@RequestBody OwnerRequest request){
        ownerService.ownerCreate(request);
        return ResponseEntity.ok().build();
    }

    // 2. 사용자 수정
    @PutMapping("/update/{ownerId}")
    private ResponseEntity<Void> ownerUpdate(
            @PathVariable Long ownerId,
            @RequestBody OwnerRequest request
    ){
        ownerService.ownerUpdate(ownerId, request);
        return ResponseEntity.ok().build();
    }

    // 3. 사용자 삭제
    @DeleteMapping("/delete/{ownerId}")
    private ResponseEntity<Void> ownerDelete(@PathVariable Long ownerId){
        ownerService.ownerDelete(ownerId);
        return ResponseEntity.ok().build();
    }
}
