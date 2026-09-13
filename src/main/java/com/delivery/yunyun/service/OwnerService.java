package com.delivery.yunyun.service;

import com.delivery.yunyun.config.security.JwtTokenProvider;
import com.delivery.yunyun.domain.Owner;
import com.delivery.yunyun.dto.request.OwnerRequest;
import com.delivery.yunyun.dto.request.owner.OwnerLoginRequest;
import com.delivery.yunyun.dto.response.LoginResponse;
import com.delivery.yunyun.error.ErrorCode;
import com.delivery.yunyun.error.CustomException;
import com.delivery.yunyun.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void ownerCreate(OwnerRequest request) {
        Owner owner = Owner.builder()
                .name(request.name())
                .userId(request.userId())
                .password(passwordEncoder.encode(request.password()))
                .roles(Collections.singletonList("ROLE_OWNER"))
                .build();
        ownerRepository.save(owner);
    }

    public void ownerUpdate(Long ownerId, OwnerRequest request) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow();

        if(request.name() != null){
            owner.setName(request.name());
        }

        if(request.userId() != null){
            owner.setUserId(request.userId());
        }

        if(request.password() != null){
            owner.setPassword(request.password());
        }

        ownerRepository.save(owner);

    }

    public void ownerDelete(Long ownerId) {
        ownerRepository.deleteById(ownerId);
    }

    public LoginResponse login(OwnerLoginRequest request) {
        Owner owner = ownerRepository.findByUserId(request.userId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(request.password(), owner.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return LoginResponse.builder()
                .id(owner.getOwnerId())
                .token(jwtTokenProvider.createToken(request.userId(), owner.getRoles()))
                .build();
    }
}
