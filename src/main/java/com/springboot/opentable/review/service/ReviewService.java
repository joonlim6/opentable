package com.springboot.opentable.review.service;

import com.springboot.opentable.exception.ErrorCode;
import com.springboot.opentable.exception.ReservationException;
import com.springboot.opentable.exception.ReviewException;
import com.springboot.opentable.reservation.domain.Reservation;
import com.springboot.opentable.reservation.repository.ReservationRepository;
import com.springboot.opentable.reservation.type.ReservationStatus;
import com.springboot.opentable.review.domain.Review;
import com.springboot.opentable.review.dto.ReviewDto;
import com.springboot.opentable.review.repository.ReviewRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReviewDto leaveReview(Long reservationId, String reviewText) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReservationException(ErrorCode.NO_SUCH_RESERVATION));

        Optional<Review> duplicate = reviewRepository.findByReservation(reservation);

        if(duplicate.isPresent()) {
            throw new ReviewException(ErrorCode.DUPLICATE_REVIEW);
        }

        if(reservation.getStatus() != ReservationStatus.ARRIVED) {
            throw new ReviewException(ErrorCode.RESERVATION_INCOMPLETE);
        }

        reservation.setStatus(ReservationStatus.COMPLETED);
        reservationRepository.save(reservation);

        Long newId = reviewRepository.findFirstByOrderByIdDesc()
            .map(review -> review.getId() + 1)
            .orElse(1L);

        return ReviewDto.fromEntity(
            reviewRepository.save(
                Review.builder()
                    .id(newId)
                    .reservation(reservation)
                    .customer(reservation.getCustomer())
                    .store(reservation.getStore())
                    .reviewText(reviewText)
                    .reviewedAt(LocalDateTime.now())
                    .build()
            )
        );
    }
}
