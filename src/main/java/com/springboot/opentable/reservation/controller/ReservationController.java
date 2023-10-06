package com.springboot.opentable.reservation.controller;

import com.springboot.opentable.reservation.dto.CheckIn;
import com.springboot.opentable.reservation.dto.MakeReservation;
import com.springboot.opentable.reservation.dto.MakeReservation.Response;
import com.springboot.opentable.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public MakeReservation.Response makeReservation(@RequestBody MakeReservation.Request request) {
        return MakeReservation.Response.from(
            reservationService.makeReservation(
                request.getCustomerId(),
                request.getStoreId(),
                request.getReservationDateTime()
            )
        );
    }

    @PutMapping("/checkin/{reservation_id}")
    public Response checkIn(@PathVariable Long reservation_id) {
        return CheckIn.Response.from(
            reservationService.checkIn(reservation_id)
        );
    }
}
