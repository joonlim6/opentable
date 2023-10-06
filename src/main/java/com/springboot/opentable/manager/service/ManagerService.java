package com.springboot.opentable.manager.service;

import com.springboot.opentable.exception.ErrorCode;
import com.springboot.opentable.exception.ManagerException;
import com.springboot.opentable.manager.domain.Manager;
import com.springboot.opentable.manager.dto.ManagerDto;
import com.springboot.opentable.manager.repository.ManagerRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;

    @Transactional
    public ManagerDto signUpManager(String email, String password, Boolean isPartner) {
        Optional<Manager> duplicate = managerRepository.findByEmail(email);

        if(duplicate.isPresent()) {
            throw new ManagerException(ErrorCode.DUPLICATE_EMAIL_MANAGER);
        }

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
