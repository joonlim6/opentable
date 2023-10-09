package com.springboot.opentable.manager.controller;

import com.springboot.opentable.manager.dto.DeleteManager;
import com.springboot.opentable.manager.dto.SignUpManager;
import com.springboot.opentable.manager.dto.UpdateManager;
import com.springboot.opentable.manager.service.ManagerService;
import com.springboot.opentable.manager.dto.ScreenReservation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerService managerService;

    // 점장 회원 가입
    @PostMapping
    public SignUpManager.Response signUpManager(@RequestBody SignUpManager.Request request) {
        return SignUpManager.Response.from(
            managerService.signUpManager(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getIsPartner())
        );
    }

    // 점장 회원 정보 수정
    @PatchMapping("/update")
    public UpdateManager.Response updateManager(@RequestBody UpdateManager.Request request) {
        return UpdateManager.Response.from(
            managerService.updateManager(
                request.getManagerId(),
                request.getPassword(),
                request.getIsPartner()
            )
        );
    }

    // 점장 회원 탈퇴
    @DeleteMapping("/delete")
    public DeleteManager.Response deleteManager(@RequestBody DeleteManager.Request request) {
        return managerService.deleteManager(
            request.getManagerId(),
            request.getEmail(),
            request.getPassword()
        );
    }

    // 점장 관할 매장 예약 조회
    @PatchMapping("/screen")
    public ScreenReservation.Response screenReservation(@RequestBody ScreenReservation.Request request) {
        return ScreenReservation.Response.from(
            managerService.screenReservation(
                request.getReservationId(),
                request.getManagerId(),
                request.getDecision()
            )
        );
    }
}
