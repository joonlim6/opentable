package com.springboot.opentable.review.dto;

import com.springboot.opentable.review.domain.Review;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long reviewId;
    private Long storeId;
    private String customerEmail;
    private String reviewText;
    private LocalDateTime reviewedAt;

    public static ReviewDto fromEntity(Review review) {
        return ReviewDto.builder()
            .reviewId(review.getId())
            .storeId(review.getStore().getId())
            .customerEmail(review.getCustomer().getEmail())
            .reviewText(review.getReviewText())
            .reviewedAt(review.getReviewedAt())
            .build();
    }
}
