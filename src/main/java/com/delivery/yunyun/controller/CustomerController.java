package com.delivery.yunyun.controller;

import com.delivery.yunyun.domain.Customer;
import com.delivery.yunyun.dto.request.customer.CustomerLoginRequest;
import com.delivery.yunyun.dto.request.customer.CustomerRequest;
import com.delivery.yunyun.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PutMapping("/update")
    private ResponseEntity<Void> customerUpdate(
            @AuthenticationPrincipal Long customerId,
            @RequestBody CustomerRequest request
    ){
        customerService.customerUpdate(customerId, request);
        return ResponseEntity.ok().build();
    }

    // 3. 사용자 삭제
    @DeleteMapping("/delete")
    private ResponseEntity<Void> customerDelete(@AuthenticationPrincipal Long customerId){
        customerService.customerDelete(customerId);
        return ResponseEntity.ok().build();
    }

    // 4. 사용자 로그인
    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody CustomerLoginRequest request){
        String token = customerService.login(request);
        return ResponseEntity.ok(token);
    }

    // 테스트
    @GetMapping("/id")
    public ResponseEntity<Long> getId(@AuthenticationPrincipal Customer customer){
        System.out.println(customer.getName());
        return ResponseEntity.ok(customer.getCustomerId());
    }
}
