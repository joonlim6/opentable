package com.springboot.opentable.store.controller;

import com.springboot.opentable.store.dto.DeleteStore;
import com.springboot.opentable.store.dto.RegisterStore;
import com.springboot.opentable.store.dto.ReviewInfo;
import com.springboot.opentable.store.dto.StoreInfo;
import com.springboot.opentable.store.dto.UpdateStore;
import com.springboot.opentable.store.service.StoreService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    // 매장 등록
    @PostMapping
    public RegisterStore.Response registerStore(@RequestBody RegisterStore.Request request) {
        return RegisterStore.Response.from(
            storeService.registerStore(
                request.getManagerId(),
                request.getName(),
                request.getLocation(),
                request.getDescription()
            )
        );
    }

    // 매장 검색
    @GetMapping
    public List<StoreInfo> getStoresByKeyword(@RequestParam("keyword") String keyword) {
        return storeService.getStoresByKeyword(keyword).stream()
            .map(storeDto -> StoreInfo.builder()
                .name(storeDto.getName())
                .location(storeDto.getLocation())
                .description(storeDto.getDescription())
                .build())
            .collect(Collectors.toList());
    }

    // 매장의 리뷰 목록 확인
    @GetMapping("/review/{store_id}")
    public List<ReviewInfo> getStoreReviews(@PathVariable Long store_id) {
        return storeService.getStoreReviews(store_id).stream()
            .map(StoreReviewDto -> ReviewInfo.builder()
                .storeId(StoreReviewDto.getStoreId())
                .customerEmail(StoreReviewDto.getCustomerEmail())
                .stars(StoreReviewDto.getStars())
                .reviewText(StoreReviewDto.getReviewText())
                .build())
            .collect(Collectors.toList());
    }

    // 매장 정보 수정
    @PatchMapping("/update")
    public UpdateStore.Response updateStore(@RequestBody UpdateStore.Request request) {
        return UpdateStore.Response.from(
            storeService.updateStore(
                request.getStoreId(),
                request.getName(),
                request.getLocation(),
                request.getDescription()
            )
        );
    }

    // 매장 등록 철회
    @DeleteMapping("/delete/{store_id}")
    public DeleteStore.Response deleteStore(@PathVariable Long store_id) {
        return storeService.deleteStore(store_id);
    }
}
