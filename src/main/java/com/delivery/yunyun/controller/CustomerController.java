package com.delivery.yunyun.controller;

import com.delivery.yunyun.domain.Customer;
import com.delivery.yunyun.dto.request.customer.CustomerLoginRequest;
import com.delivery.yunyun.dto.request.customer.CustomerRequest;
import com.delivery.yunyun.dto.response.CustomerInfoResponse;
import com.delivery.yunyun.dto.response.LoginResponse;
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
    private ResponseEntity<LoginResponse> login(@RequestBody CustomerLoginRequest request){
        LoginResponse loginResponse = customerService.login(request);
        return ResponseEntity.ok(loginResponse);
    }

    // 5. 사용자 정보
    @GetMapping("/info")
    private ResponseEntity<CustomerInfoResponse> customerInfo(@AuthenticationPrincipal Customer customer){
        CustomerInfoResponse customerInfoResponse = customerService.customerInfo(customer);
        return ResponseEntity.ok(customerInfoResponse);
    }


    // 테스트
    @GetMapping("/id")
    public ResponseEntity<Long> getId(@AuthenticationPrincipal Customer customer){
        System.out.println(customer.getName());
        return ResponseEntity.ok(customer.getCustomerId());
    }
}
