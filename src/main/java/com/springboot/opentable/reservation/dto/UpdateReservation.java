package com.springboot.opentable.reservation.dto;

import com.springboot.opentable.reservation.type.ReservationStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UpdateReservation {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long reservationId;
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
        private String storeName;
        private LocalDateTime reservationDateTime;
        private ReservationStatus status;

        public static UpdateReservation.Response from(ReservationDto reservationDto) {
            return UpdateReservation.Response.builder()
                .reservationId(reservationDto.getReservationId())
                .storeName(reservationDto.getStoreName())
                .reservationDateTime(reservationDto.getReservationDateTime())
                .status(reservationDto.getStatus())
                .build();
        }
    }
}
