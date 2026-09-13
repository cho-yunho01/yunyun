package com.delivery.yunyun.service;

import com.delivery.yunyun.config.security.JwtTokenProvider;
import com.delivery.yunyun.domain.Customer;
import com.delivery.yunyun.dto.request.customer.CustomerLoginRequest;
import com.delivery.yunyun.dto.request.customer.CustomerRequest;
import com.delivery.yunyun.dto.response.CustomerInfoResponse;
import com.delivery.yunyun.dto.response.LoginResponse;
import com.delivery.yunyun.error.ErrorCode;
import com.delivery.yunyun.error.CustomException;
import com.delivery.yunyun.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void customerCreate(CustomerRequest request) {
        if(customerRepository.findByUserId(request.userId()).isPresent()){
            new CustomException(ErrorCode.DUPLICATE_USER_ID);
        };

        Customer customer = Customer.builder()
                .name(request.name())
                .userId(request.userId())
                .password(passwordEncoder.encode(request.password()))
                .balance(request.balance())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        customerRepository.save(customer);
    }

    public void customerUpdate(Long customerId, CustomerRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();

        if(request.name() != null){
            customer.setName(request.name());
        }

        if(request.userId() != null){
            customer.setUserId(request.userId());
        }

        if(request.password() != null){
            customer.setPassword(request.password());
        }

        if(request.balance() != null){
            customer.setBalance(request.balance());
        }

        customerRepository.save(customer);

    }

    public void customerDelete(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    public LoginResponse login(CustomerLoginRequest request) {
        Customer customer = customerRepository.findByUserId(request.userId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)); // 사용자가 존재하지 않음

        if(!passwordEncoder.matches(request.password(), customer.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD); // 패스워드 일치 오류
        }
        return LoginResponse.builder()
                .id(customer.getCustomerId())
                .token(jwtTokenProvider.createToken(request.userId(), customer.getRoles()))
                .build();

    }




    public CustomerInfoResponse customerInfo(Customer customer) {
        return CustomerInfoResponse.builder()
                .name(customer.getName())
                .userId(customer.getUserId())
                .balance(customer.getBalance())
                .roles(customer.getRoles())
                .build();
    }
}
