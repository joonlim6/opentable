package com.springboot.opentable.review.dto;

import com.springboot.opentable.manager.dto.ManagerDto;
import com.springboot.opentable.manager.dto.SignUpManager;
import com.springboot.opentable.manager.dto.SignUpManager.Response;
import java.time.LocalDateTime;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class LeaveReview {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long reservationId;

        @NotNull
        @Min(1)
        @Max(5)
        private Integer stars;

        private String reviewText;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long reviewId;
        private Long storeId;
        private String customerEmail;
        private Integer stars;
        private String reviewText;
        private LocalDateTime reviewedAt;

        public static Response from(ReviewDto reviewDto) {
            return Response.builder()
                .reviewId(reviewDto.getReviewId())
                .storeId(reviewDto.getStoreId())
                .customerEmail(reviewDto.getCustomerEmail())
                .stars(reviewDto.getStars())
                .reviewText(reviewDto.getReviewText())
                .reviewedAt(reviewDto.getReviewedAt())
                .build();
        }
    }
}
