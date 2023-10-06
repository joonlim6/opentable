package com.springboot.opentable.reservation.repository;

import com.springboot.opentable.customer.domain.Customer;
import com.springboot.opentable.reservation.domain.Reservation;
import com.springboot.opentable.store.domain.Store;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findFirstByOrderByIdDesc();
    Optional<Reservation> findByStoreAndCustomerAndReservationDateTime(Store store, Customer customer, LocalDateTime reservationTime);
}
