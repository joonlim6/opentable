package com.springboot.opentable.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class DeleteManager {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long managerId;
        private String email;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long managerId;
        private Boolean deleted;
    }
}
