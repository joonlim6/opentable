package com.springboot.opentable.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UpdateManager {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long managerId;
        private String password;
        private Boolean isPartner;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String email;
        private boolean isPartner;

        public static UpdateManager.Response from(ManagerDto manager) {
            return UpdateManager.Response.builder()
                .id(manager.getUserId())
                .email(manager.getEmail())
                .isPartner(manager.getIsPartner())
                .build();
        }
    }
}
