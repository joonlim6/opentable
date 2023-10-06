package com.springboot.opentable.reservation.service;

import com.springboot.opentable.customer.domain.Customer;
import com.springboot.opentable.customer.repository.CustomerRepository;
import com.springboot.opentable.reservation.domain.Reservation;
import com.springboot.opentable.reservation.dto.ReservationDto;
import com.springboot.opentable.reservation.repository.ReservationRepository;
import com.springboot.opentable.reservation.type.ReservationStatus;
import com.springboot.opentable.store.domain.Store;
import com.springboot.opentable.store.repository.StoreRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public ReservationDto makeReservation(Long customerId, Long storeId, LocalDateTime reservationDateTime) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        Store store = storeRepository.findById(storeId).orElseThrow();

        Long newId = reservationRepository.findFirstByOrderByIdDesc()
            .map(reservation -> reservation .getId() + 1)
            .orElse(1L);

        // check if there's an overlap

        return ReservationDto.fromEntity(
            reservationRepository.save(Reservation.builder()
                .id(newId)
                .store(store)
                .customer(customer)
                .reservationDateTime(reservationDateTime)
                .status(ReservationStatus.CONFIRMED)
                .build())
        );
    }
}
