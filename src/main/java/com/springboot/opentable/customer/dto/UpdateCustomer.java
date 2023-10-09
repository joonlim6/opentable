package com.springboot.opentable.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UpdateCustomer {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long customerId;
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

        public static Response from(CustomerDto customer) {
            return Response.builder()
                .id(customer.getUserId())
                .email(customer.getEmail())
                .build();
        }
    }
}
