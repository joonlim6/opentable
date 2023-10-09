package com.springboot.opentable.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewInfo {
    private Long storeId;
    private String customerEmail;
    private Integer stars;
    private String reviewText;
}
