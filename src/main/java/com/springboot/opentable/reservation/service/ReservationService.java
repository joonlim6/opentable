package com.springboot.opentable.reservation.service;

import static com.springboot.opentable.exception.ErrorCode.CUSTOMER_ALREADY_ARRIVED;
import static com.springboot.opentable.exception.ErrorCode.DENIED_RESERVATION;
import static com.springboot.opentable.exception.ErrorCode.DUPLICATE_RESERVATION;
import static com.springboot.opentable.exception.ErrorCode.INVALID_DECISION;
import static com.springboot.opentable.exception.ErrorCode.LATE_CHECK_IN;
import static com.springboot.opentable.exception.ErrorCode.NOT_IN_CHARGE;
import static com.springboot.opentable.exception.ErrorCode.NO_SUCH_CUSTOMER;
import static com.springboot.opentable.exception.ErrorCode.NO_SUCH_RESERVATION;
import static com.springboot.opentable.exception.ErrorCode.NO_SUCH_STORE;
import static com.springboot.opentable.exception.ErrorCode.RESERVATION_ALREADY_CANCELLED;

import com.springboot.opentable.customer.domain.Customer;
import com.springboot.opentable.customer.repository.CustomerRepository;
import com.springboot.opentable.exception.CustomerException;
import com.springboot.opentable.exception.ManagerException;
import com.springboot.opentable.exception.ReservationException;
import com.springboot.opentable.exception.StoreException;
import com.springboot.opentable.manager.domain.Manager;
import com.springboot.opentable.reservation.domain.Reservation;
import com.springboot.opentable.reservation.dto.ReservationDto;
import com.springboot.opentable.reservation.repository.ReservationRepository;
import com.springboot.opentable.reservation.type.ReservationStatus;
import com.springboot.opentable.store.domain.Store;
import com.springboot.opentable.store.repository.StoreRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
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
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerException(NO_SUCH_CUSTOMER));
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new StoreException(NO_SUCH_STORE));

        Optional<Reservation> duplicate = reservationRepository.findByStoreAndCustomerAndReservationDateTime(store, customer, reservationDateTime);

        if(duplicate.isPresent()) {
            throw new ReservationException(DUPLICATE_RESERVATION);
        }

        Long newId = reservationRepository.findFirstByOrderByIdDesc()
            .map(reservation -> reservation .getId() + 1)
            .orElse(1L);

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

    @Transactional
    public ReservationDto checkIn(Long reservationId) {
        Reservation reservation = getReservation(reservationId);

        checkStatus(reservation);

        LocalDateTime curr = LocalDateTime.now();

        if(curr.until(reservation.getReservationDateTime(), ChronoUnit.SECONDS) < 600) {
            throw new ReservationException(LATE_CHECK_IN);
        }

        reservation.setArrivedAt(curr);
        reservation.setStatus(ReservationStatus.ARRIVED);

        return ReservationDto.fromEntity(
            reservationRepository.save(reservation)
        );
    }

    @Transactional
    public ReservationDto updateReservation(Long reservationId, Long storeId, LocalDateTime reservationDateTime) {
        Reservation reservation = getReservation(reservationId);

        if(!reservation.getStore().getId().equals(storeId)) {
            Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(NO_SUCH_STORE));
            reservation.setStore(store);
        }

        if(!reservation.getReservationDateTime().equals(reservationDateTime)) {
            reservation.setReservationDateTime(reservationDateTime);
        }

        return ReservationDto.fromEntity(
            reservationRepository.save(reservation)
        );
    }

    @Transactional
    public ReservationDto cancelReservation(Long reservationId) {
        Reservation reservation = getReservation(reservationId);

        reservation.setStatus(ReservationStatus.CANCELLED);

        return ReservationDto.fromEntity(
            reservationRepository.save(reservation)
        );
    }


    @Transactional
    public ReservationDto screenReservation(Long reservationId, Long managerId, String decision) {
        Reservation reservation = getReservation(reservationId);
        Manager manager = reservation.getStore().getManager();

        if (manager.getId() != managerId) {
            throw new ManagerException(NOT_IN_CHARGE);
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

    public void checkStatus(Reservation reservation) {
        if(reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new ReservationException(RESERVATION_ALREADY_CANCELLED);
        }

        if(reservation.getStatus() == ReservationStatus.REFUSED) {
            throw new ReservationException(DENIED_RESERVATION);
        }

        if(reservation.getStatus() == ReservationStatus.ARRIVED) {
            throw new ReservationException(CUSTOMER_ALREADY_ARRIVED);
        }
    }

    public Reservation getReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReservationException(NO_SUCH_RESERVATION));

        return reservation;
    }

}
