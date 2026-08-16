package com.delivery.yunyun.service;

import com.delivery.yunyun.domain.Customer;
import com.delivery.yunyun.dto.request.CustomerRequest;
import com.delivery.yunyun.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public void customerCreate(CustomerRequest request) {
        Customer customer = Customer.builder()
                .name(request.name())
                .userId(request.userId())
                .password(request.password())
                .balance(request.balance())
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
}
