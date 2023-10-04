package com.springboot.opentable.customer.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class SignUpCustomer {

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Request {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String email;
        private LocalDateTime registeredAt;

        public static Response from(CustomerDto manager) {
            return Response.builder()
                .id(manager.getUserId())
                .email(manager.getEmail())
                .registeredAt(manager.getRegisteredAt())
                .build();
        }
    }
}
