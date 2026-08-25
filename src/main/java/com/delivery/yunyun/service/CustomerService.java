package com.delivery.yunyun.service;

import com.delivery.yunyun.config.security.JwtTokenProvider;
import com.delivery.yunyun.domain.Customer;
import com.delivery.yunyun.dto.request.customer.CustomerLoginRequest;
import com.delivery.yunyun.dto.request.customer.CustomerRequest;
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

    public String login(CustomerLoginRequest request) {
        Customer customer = customerRepository.findByUserId(request.userId());
        if(!passwordEncoder.matches(request.password(), customer.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        return jwtTokenProvider.createToken(request.userId(), customer.getRoles());
    }
}
