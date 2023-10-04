package com.springboot.opentable.manager.service;

import com.springboot.opentable.manager.domain.Manager;
import com.springboot.opentable.manager.dto.ManagerDto;
import com.springboot.opentable.manager.repository.ManagerRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;

    public ManagerDto signUpManager(String email, String password, Boolean isPartner) {
        Long newId = managerRepository.findFirstByOrderByIdDesc()
            .map(manager -> manager.getId() + 1)
            .orElse(1L);

        return ManagerDto.fromEntity(
            managerRepository.save(Manager.builder()
                .id(newId)
                .email(email)
                .password(password)
                .isPartner(isPartner)
                .registeredAt(LocalDateTime.now())
                .build())
        );
    }
}
