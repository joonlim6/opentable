package com.springboot.opentable.reservation.service;

import static com.springboot.opentable.exception.ErrorCode.DUPLICATE_RESERVATION;
import static com.springboot.opentable.exception.ErrorCode.LATE_CHECK_IN;
import static com.springboot.opentable.exception.ErrorCode.NO_SUCH_CUSTOMER;
import static com.springboot.opentable.exception.ErrorCode.NO_SUCH_RESERVATION;
import static com.springboot.opentable.exception.ErrorCode.NO_SUCH_STORE;
import static com.springboot.opentable.exception.ErrorCode.RESERVATION_UNAPPROVED;

import com.springboot.opentable.customer.domain.Customer;
import com.springboot.opentable.customer.repository.CustomerRepository;
import com.springboot.opentable.exception.CustomerException;
import com.springboot.opentable.exception.ReservationException;
import com.springboot.opentable.exception.StoreException;
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

        // 동일 고객이 동일 매장에 동일 시간 예약 불가
        if(duplicate.isPresent()) {
            throw new ReservationException(DUPLICATE_RESERVATION);
        }

        Long reservationId = reservationRepository.findFirstByOrderByIdDesc()
            .map(reservation -> reservation .getId() + 1)
            .orElse(1L);

        return ReservationDto.fromEntity(
            reservationRepository.save(Reservation.builder()
                .id(reservationId)
                .store(store)
                .customer(customer)
                .reservationDateTime(reservationDateTime)
                .status(ReservationStatus.REQUESTED)
                .build())
        );
    }

    @Transactional
    public ReservationDto checkIn(Long reservationId) {
        Reservation reservation = getReservation(reservationId);

        // 점장이 예약 승인을 했어야 방문 확인이 가능
        if(reservation.getStatus() != ReservationStatus.APPROVED) {
            throw new ReservationException(RESERVATION_UNAPPROVED);
        }

        LocalDateTime curr = LocalDateTime.now();

        // 예약 10 분전에 도착 했어야 방문 확인이 가능
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

        // 매장 변경 가능
        if(!reservation.getStore().getId().equals(storeId)) {
            Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(NO_SUCH_STORE));
            reservation.setStore(store);
        }

        // 예약 일 및 시간 수정 가능
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

    public Reservation getReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReservationException(NO_SUCH_RESERVATION));

        return reservation;
    }

}
