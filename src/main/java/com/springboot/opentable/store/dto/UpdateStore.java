package com.springboot.opentable.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UpdateStore {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long storeId;
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
        private Long storeId;
        private String name;
        private String location;
        private String description;

        public static UpdateStore.Response from(StoreDto storeDto) {
            return Response.builder()
                .storeId(storeDto.getStoreId())
                .name(storeDto.getName())
                .location(storeDto.getLocation())
                .description(storeDto.getDescription())
                .build();
        }
    }
}
