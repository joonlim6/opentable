package com.springboot.opentable.store.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RegisterStore {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long managerId;
        private String name;
        private String location;
        private String description;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private Long managerId;
        private String name;
        private String location;
        private String description;
        private LocalDateTime registeredAt;

        public static RegisterStore.Response from(StoreDto storeDto) {
            return Response.builder()
                .id(storeDto.getStoreId())
                .managerId(storeDto.getManagerId())
                .name(storeDto.getName())
                .location(storeDto.getLocation())
                .description(storeDto.getDescription())
                .registeredAt(storeDto.getRegisteredAt())
                .build();
        }
    }
}
