package com.springboot.opentable.store.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class DeleteStore {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long storeId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long storeId;
        private Boolean deleted;
    }
}
