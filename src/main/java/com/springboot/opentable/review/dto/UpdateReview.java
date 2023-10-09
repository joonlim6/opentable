package com.springboot.opentable.review.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UpdateReview {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long reviewId;
        private Long customerId;

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
        private Integer stars;
        private String reviewText;

        public static UpdateReview.Response from(ReviewDto reviewDto) {
            return Response.builder()
                .reviewId(reviewDto.getReviewId())
                .stars(reviewDto.getStars())
                .reviewText(reviewDto.getReviewText())
                .build();
        }
    }
}
