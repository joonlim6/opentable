package com.springboot.opentable.reservation.dto;

import com.springboot.opentable.reservation.type.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ScreenReservation {
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long managerId;
        private Long reservationId;
        private String decision;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long reservationId;
        private ReservationStatus status;

        public static ScreenReservation.Response from(ReservationDto reservationDto) {
            return ScreenReservation.Response.builder()
                .reservationId(reservationDto.getReservationId())
                .status(reservationDto.getStatus())
                .build();
        }
    }
}
