package com.springboot.opentable.manager.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CreateAccount {

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Request {
        private String email;
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
        private LocalDateTime registeredAt;

        public static Response from(ManagerDto manager) {
            return Response.builder()
                .id(manager.getUserId())
                .email(manager.getEmail())
                .isPartner(manager.getIsPartner())
                .registeredAt(manager.getRegisteredAt())
                .build();
        }
    }
}
