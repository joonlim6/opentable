package com.springboot.opentable.reservation.dto;

import com.springboot.opentable.reservation.type.ReservationStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CheckIn {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long reservationId;
        private String storeName;
        private LocalDateTime reservationDateTime;
        private LocalDateTime arrivedAt;
        private ReservationStatus status;

        public static CheckIn.Response from(ReservationDto reservationDto) {
            return Response.builder()
                .reservationId(reservationDto.getReservationId())
                .storeName(reservationDto.getStoreName())
                .reservationDateTime(reservationDto.getReservationDateTime())
                .arrivedAt(reservationDto.getArrivedAt())
                .status(reservationDto.getStatus())
                .build();
        }
    }
}
