package com.springboot.opentable.store.controller;

import com.springboot.opentable.store.dto.RegisterStore;
import com.springboot.opentable.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
