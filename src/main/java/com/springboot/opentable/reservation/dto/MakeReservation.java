package com.springboot.opentable.reservation.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MakeReservation {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long customerId;
        private Long storeId;

        private LocalDateTime reservationDateTime;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long reservationId;
        private Long storeId;
        private String storeName;
        private LocalDateTime reservationDateTime;

        public static Response from(ReservationDto reservationDto) {
            return Response.builder()

                .build();
        }
    }
}
