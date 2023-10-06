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
        private Long storeId;
        private String storeName;
        private LocalDateTime reservationDateTime;
        private ReservationStatus status;

        public static MakeReservation.Response from(ReservationDto reservationDto) {
            return MakeReservation.Response.builder()
                .reservationId(reservationDto.getReservationId())
                .storeId(reservationDto.getStoreId())
                .storeName(reservationDto.getStoreName())
                .reservationDateTime(reservationDto.getReservationDateTime())
                .status(reservationDto.getStatus())
                .build();
        }
    }
}
