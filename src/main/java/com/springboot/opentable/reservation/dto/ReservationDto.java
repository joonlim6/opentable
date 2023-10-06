package com.springboot.opentable.reservation.dto;

import com.springboot.opentable.reservation.domain.Reservation;
import com.springboot.opentable.reservation.type.ReservationStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {
    private Long reservationId;
    private Long storeId;
    private String storeName;
    private LocalDateTime reservationDateTime;
    private ReservationStatus status;

    public static ReservationDto fromEntity(Reservation reservation) {
        return ReservationDto.builder()
            .reservationId(reservation.getId())
            .storeId(reservation.getStore().getId())
            .storeName(reservation.getStore().getName())
            .reservationDateTime(reservation.getReservationDateTime())
            .status(reservation.getStatus())
            .build();
    }
}
