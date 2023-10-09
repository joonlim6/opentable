package com.springboot.opentable.store.dto;

import com.springboot.opentable.review.domain.Review;
import com.springboot.opentable.store.domain.Store;
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
public class StoreReviewDto {
    private Long storeId;
    private String customerEmail;
    private Integer stars;
    private String reviewText;

    public static StoreReviewDto fromEntity(Review review) {
        return StoreReviewDto.builder()
            .storeId(review.getStore().getId())
            .customerEmail(review.getCustomer().getEmail())
            .stars(review.getStars())
            .reviewText(review.getReviewText())
            .build();
    }
}
