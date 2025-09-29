package com.example.cardatabase4.service;

import com.example.cardatabase4.domain.Owner;
import com.example.cardatabase4.domain.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    // 전체 조회
    public List<Owner> getOwners() {
        return ownerRepository.findAll();
    }

    // id 별 조회
    public Optional<Owner> getOwnerById(Long id) {
        return ownerRepository.findById(id);
    }

    // Owner 객체 추가
    public Owner addOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    // Owner 객체 삭제
    public boolean deleteOwner(Long id) {
        if(ownerRepository.existsById(id)) {
            ownerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Owner 객체 수정
    @Transactional
    public Optional<Owner> updateOwner(Long id, Owner ownerDetails) {
        return ownerRepository.findById(id)
                .map(owner -> {
                    owner.setLastName(ownerDetails.getLastName());
                    owner.setFirstName(ownerDetails.getFirstName());
                    return owner;
                });
    }


}
