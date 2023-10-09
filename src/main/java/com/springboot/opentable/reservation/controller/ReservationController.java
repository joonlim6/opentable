package com.springboot.opentable.reservation.controller;

import com.springboot.opentable.reservation.dto.CancelReservation;
import com.springboot.opentable.reservation.dto.CheckIn;
import com.springboot.opentable.reservation.dto.MakeReservation;
import com.springboot.opentable.manager.dto.ScreenReservation;
import com.springboot.opentable.reservation.dto.UpdateReservation;
import com.springboot.opentable.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PatchMapping("/checkin/{reservation_id}")
    public CheckIn.Response checkIn(@PathVariable Long reservation_id) {
        return CheckIn.Response.from(
            reservationService.checkIn(reservation_id)
        );
    }

    @PatchMapping("/update")
    public UpdateReservation.Response updateReservation(@RequestBody UpdateReservation.Request request) {
        return UpdateReservation.Response.from(
            reservationService.updateReservation(
                request.getReservationId(),
                request.getStoreId(),
                request.getReservationDateTime()
            )
        );
    }

    @PatchMapping("/cancel/{reservation_id}")
    public CancelReservation.Response cancelReservation(@PathVariable Long reservation_id) {
        return CancelReservation.Response.from(
            reservationService.cancelReservation(reservation_id)
        );
    }

//    @PatchMapping("/screen")
//    public ScreenReservation.Response screenReservation(@RequestBody ScreenReservation.Request request) {
//        return ScreenReservation.Response.from(
//            reservationService.screenReservation(
//                request.getReservationId(),
//                request.getManagerId(),
//                request.getDecision()
//            )
//        );
//    }
}
