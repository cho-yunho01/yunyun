package com.delivery.yunyun.service;

import com.delivery.yunyun.domain.Owner;
import com.delivery.yunyun.dto.request.OwnerRequest;
import com.delivery.yunyun.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

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
}
