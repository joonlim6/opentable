package com.springboot.opentable.review.controller;

import com.springboot.opentable.review.dto.DeleteReview;
import com.springboot.opentable.review.dto.LeaveReview;
import com.springboot.opentable.review.dto.UpdateReview;
import com.springboot.opentable.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
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
                request.getStars(),
                request.getReviewText()
            )
        );
    }

    @PatchMapping("/update")
    public UpdateReview.Response updateReview(@RequestBody UpdateReview.Request request) {
        return UpdateReview.Response.from(
            reviewService.updateReview(
                request.getReviewId(),
                request.getCustomerId(),
                request.getStars(),
                request.getReviewText()
            )
        );
    }

    @DeleteMapping("/delete/{review_id}")
    public DeleteReview.Response deleteReview(@PathVariable Long review_id) {
        return reviewService.deleteReview(review_id);
    }
}
