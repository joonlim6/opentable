package com.springboot.opentable.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.opentable.reservation.type.ReservationStatus;
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

        @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
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

        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        private LocalDateTime reservationDateTime;

        private ReservationStatus status;

        public static Response from(ReservationDto reservationDto) {
            return Response.builder()
                .reservationId(reservationDto.getReservationId())
                .storeName(reservationDto.getStoreName())
                .reservationDateTime(reservationDto.getReservationDateTime())
                .status(reservationDto.getStatus())
                .build();
        }
    }
}
