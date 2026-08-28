package com.delivery.yunyun.service;

import com.delivery.yunyun.config.security.JwtTokenProvider;
import com.delivery.yunyun.domain.Owner;
import com.delivery.yunyun.dto.request.OwnerRequest;
import com.delivery.yunyun.dto.request.owner.OwnerLoginRequest;
import com.delivery.yunyun.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                .password(request.password())
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

    public String login(OwnerLoginRequest request) {
        Owner owner = ownerRepository.findByUserId(request.userId());

        if(!passwordEncoder.matches(request.password(), owner.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        return jwtTokenProvider.createToken(request.userId(), owner.getRoles());
    }
}
