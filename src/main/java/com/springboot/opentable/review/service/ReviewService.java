package com.springboot.opentable.review.service;

import com.springboot.opentable.exception.ErrorCode;
import com.springboot.opentable.exception.ReservationException;
import com.springboot.opentable.exception.ReviewException;
import com.springboot.opentable.reservation.domain.Reservation;
import com.springboot.opentable.reservation.repository.ReservationRepository;
import com.springboot.opentable.reservation.type.ReservationStatus;
import com.springboot.opentable.review.domain.Review;
import com.springboot.opentable.review.dto.DeleteReview;
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
    public ReviewDto leaveReview(Long reservationId, Integer stars, String reviewText) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReservationException(ErrorCode.NO_SUCH_RESERVATION));

        Optional<Review> duplicate = reviewRepository.findByReservation(reservation);

        // 예약 당 한 개의 리뷰만 남길 수 있음
        if(duplicate.isPresent()) {
            throw new ReviewException(ErrorCode.DUPLICATE_REVIEW);
        }

        // 방문이 확인 됐을 시에만 리뷰 작성 가능
        if(reservation.getStatus() != ReservationStatus.ARRIVED) {
            throw new ReviewException(ErrorCode.RESERVATION_INCOMPLETE);
        }

        reservation.setStatus(ReservationStatus.COMPLETED);
        reservationRepository.save(reservation);

        Long reviewId = reviewRepository.findFirstByOrderByIdDesc()
            .map(review -> review.getId() + 1)
            .orElse(1L);

        return ReviewDto.fromEntity(
            reviewRepository.save(
                Review.builder()
                    .id(reviewId)
                    .reservation(reservation)
                    .store(reservation.getStore())
                    .stars(stars)
                    .reviewText(reviewText)
                    .reviewedAt(LocalDateTime.now())
                    .build()
            )
        );
    }

    @Transactional
    public ReviewDto updateReview(Long reviewId, Long customerId, Integer stars, String reviewText) {
        Review review = getReview(reviewId);

        // 해당 리뷰의 고객이 일치해야 수정 가능
        if(review.getReservation().getCustomer().getId() != customerId) {
            throw new ReviewException(ErrorCode.NOT_YOUR_REVIEW);
        }

        // 별점 및 리뷰 내용 수정 가능
        review.setStars(stars);
        review.setReviewText(reviewText);

        return ReviewDto.fromEntity(reviewRepository.save(review));
    }

    @Transactional
    public DeleteReview.Response deleteReview(Long reviewId) {
        Review review = getReview(reviewId);

        reviewRepository.delete(review);

        return DeleteReview.Response.builder()
            .reviewId(reviewId)
            .deleted(true)
            .build();
    }

    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ReviewException(ErrorCode.NO_SUCH_REVIEW));
    }
}
