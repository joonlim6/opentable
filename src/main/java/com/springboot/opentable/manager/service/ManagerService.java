package com.springboot.opentable.manager.service;

import static com.springboot.opentable.exception.ErrorCode.CANT_DECIDE;
import static com.springboot.opentable.exception.ErrorCode.INVALID_DECISION;
import static com.springboot.opentable.exception.ErrorCode.NOT_IN_CHARGE;
import static com.springboot.opentable.exception.ErrorCode.NO_SUCH_RESERVATION;

import com.springboot.opentable.exception.ErrorCode;
import com.springboot.opentable.exception.ManagerException;
import com.springboot.opentable.exception.ReservationException;
import com.springboot.opentable.manager.domain.Manager;
import com.springboot.opentable.manager.dto.DeleteManager;
import com.springboot.opentable.manager.dto.ManagerDto;
import com.springboot.opentable.manager.repository.ManagerRepository;
import com.springboot.opentable.reservation.domain.Reservation;
import com.springboot.opentable.reservation.dto.ReservationDto;
import com.springboot.opentable.reservation.repository.ReservationRepository;
import com.springboot.opentable.reservation.type.ReservationStatus;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ManagerDto signUpManager(String email, String password, String name, Boolean isPartner) {
        Optional<Manager> duplicate = managerRepository.findByEmail(email);

        if(duplicate.isPresent()) {
            throw new ManagerException(ErrorCode.DUPLICATE_EMAIL_MANAGER);
        }

        Long managerId = managerRepository.findFirstByOrderByIdDesc()
            .map(manager -> manager.getId() + 1)
            .orElse(1L);

        return ManagerDto.fromEntity(
            managerRepository.save(Manager.builder()
                .id(managerId)
                .email(email)
                .password(password)
                .name(name)
                .isPartner(isPartner)
                .registeredAt(LocalDateTime.now())
                .build())
        );
    }

    @Transactional
    public ManagerDto updateManager(Long managerId, String password, Boolean isPartner) {
        Manager manager = getManager(managerId);

        manager.setPassword(password);
        manager.setIsPartner(isPartner);

        return ManagerDto.fromEntity(
            managerRepository.save(manager)
        );
    }

    @Transactional
    public DeleteManager.Response deleteManager(Long managerId, String email, String password) {
        Manager manager = getManager(managerId);

        if(!manager.getEmail().equals(email) || !manager.getPassword().equals(password)) {
            throw new ManagerException(ErrorCode.WRONG_EMAIL_OR_PASSWORD);
        }

        managerRepository.delete(manager);

        return DeleteManager.Response.builder()
            .managerId(managerId)
            .deleted(true)
            .build();
    }

    @Transactional
    public ReservationDto screenReservation(Long reservationId, Long managerId, String decision) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReservationException(NO_SUCH_RESERVATION));

        Manager manager = getManager(managerId);

        if (!reservation.getStore().getManager().equals(manager)) {
            throw new ManagerException(NOT_IN_CHARGE);
        }

        if(reservation.getStatus() != ReservationStatus.REQUESTED) {
            throw new ManagerException(CANT_DECIDE);
        }

        if (decision.equalsIgnoreCase("approve")) {
            reservation.setStatus(ReservationStatus.APPROVED);
        }   else if (decision.equalsIgnoreCase("refuse")) {
            reservation.setStatus(ReservationStatus.REFUSED);
        }   else {
            throw new ManagerException(INVALID_DECISION);
        }

        return ReservationDto.fromEntity(
            reservationRepository.save(reservation)
        );
    }

    public Manager getManager(Long managerId) {
        return managerRepository.findById(managerId)
            .orElseThrow(() -> new ManagerException(ErrorCode.NO_SUCH_MANAGER));
    }

}
