package com.springboot.opentable.reservation.dto;

import com.springboot.opentable.reservation.type.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CancelReservation {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long reservationId;
        private ReservationStatus status;

        public static CancelReservation.Response from(ReservationDto reservationDto) {
            return CancelReservation.Response.builder()
                .reservationId(reservationDto.getReservationId())
                .status(reservationDto.getStatus())
                .build();
        }
    }
}
