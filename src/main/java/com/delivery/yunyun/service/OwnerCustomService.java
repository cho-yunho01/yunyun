package com.delivery.yunyun.service;

import com.delivery.yunyun.repository.OwnerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class OwnerCustomService implements UserDetailsService {
    private OwnerRepository ownerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ownerRepository.findByUserId(username);
    }
}
