package com.springboot.opentable.store.controller;

import com.springboot.opentable.store.dto.RegisterStore;
import com.springboot.opentable.store.dto.StoreInfo;
import com.springboot.opentable.store.service.StoreService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
        return  storeService.getStoresByKeyword(keyword).stream()
            .map(storeDto -> StoreInfo.builder()
                .name(storeDto.getName())
                .location(storeDto.getLocation())
                .description(storeDto.getDescription())
                .build())
            .collect(Collectors.toList());
    }
}
