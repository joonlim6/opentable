package com.springboot.opentable.store.service;

import com.springboot.opentable.manager.domain.Manager;
import com.springboot.opentable.manager.repository.ManagerRepository;
import com.springboot.opentable.store.domain.Store;
import com.springboot.opentable.store.dto.StoreDto;
import com.springboot.opentable.store.repository.StoreRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final ManagerRepository managerRepository;

    @Transactional
    public StoreDto registerStore(Long managerId, String name, String location, String description) {
        Manager manager = managerRepository.findById(managerId)
            .orElseThrow();

        if(!manager.getIsPartner()) {
            throw new RuntimeException("Logged in manager is not a partner");
        }

        Long newId = managerRepository.findFirstByOrderByIdDesc()
            .map(store -> store.getId() + 1)
            .orElse(1L);

        return StoreDto.fromEntity(
            storeRepository.save(Store.builder()
                .id(newId)
                .manager(manager)
                .name(name)
                .location(location)
                .description(description)
                .registeredAt(LocalDateTime.now())
                .build())
        );
    }
}
