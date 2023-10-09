package com.springboot.opentable.review.repository;

import com.springboot.opentable.reservation.domain.Reservation;
import com.springboot.opentable.review.domain.Review;
import com.springboot.opentable.store.domain.Store;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findFirstByOrderByIdDesc();
    Optional<Review> findByReservation(Reservation reservation);
    List<Review> findByStore(Store store);
}
