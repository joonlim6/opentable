package com.springboot.opentable.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class DeleteCustomer {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long customerId;
        private String email;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long customerId;
        private Boolean deleted;
    }
}

