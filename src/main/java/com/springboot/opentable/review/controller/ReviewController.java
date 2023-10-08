package com.springboot.opentable.review.controller;

import com.springboot.opentable.review.dto.LeaveReview;
import com.springboot.opentable.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping()
    public LeaveReview.Response leaveReview(@RequestBody LeaveReview.Request request) {
        return LeaveReview.Response.from(
            reviewService.leaveReview(
                request.getReservationId(),
                request.getReviewText()
            )
        );
    }
}
