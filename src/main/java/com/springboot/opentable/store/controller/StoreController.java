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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    @PutMapping
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

    @GetMapping("/review/{store_id}")
    public List<ReviewInfo> getStoreReviews(@PathVariable Long store_id) {
        return storeService.getStoreReviews(store_id).stream()
            .map(StoreReviewDto -> ReviewInfo.builder()
                .storeId(StoreReviewDto.getStoreId())
                .customerEmail(StoreReviewDto.getCustomerEmail())
                .reviewText(StoreReviewDto.getReviewText())
                .build())
            .collect(Collectors.toList());
    }

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

    @DeleteMapping("/delete")
    public DeleteStore.Response updateStore(@RequestBody DeleteStore.Request request) {
        return storeService.deleteStore(request.getStoreId());
    }
}
