package com.delivery.yunyun.controller;

import com.delivery.yunyun.dto.request.customer.CustomerRequest;
import com.delivery.yunyun.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    // 1. 사용자 추가
    @PostMapping("/create")
    private ResponseEntity<Void> customerCreate(@RequestBody CustomerRequest request){
        customerService.customerCreate(request);
        return ResponseEntity.ok().build();
    }

    // 2. 사용자 수정
    @PutMapping("/update/{customerId}")
    private ResponseEntity<Void> customerUpdate(
            @PathVariable Long customerId,
            @RequestBody CustomerRequest request
    ){
        customerService.customerUpdate(customerId, request);
        return ResponseEntity.ok().build();
    }

    // 3. 사용자 삭제
    @DeleteMapping("/delete/{customerId}")
    private ResponseEntity<Void> customerDelete(@PathVariable Long customerId){
        customerService.customerDelete(customerId);
        return ResponseEntity.ok().build();
    }
}
