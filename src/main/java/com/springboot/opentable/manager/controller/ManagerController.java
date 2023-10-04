package com.springboot.opentable.manager.controller;

import com.springboot.opentable.manager.dto.SignUpManager;
import com.springboot.opentable.manager.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping
    public SignUpManager.Response signUpManager(@RequestBody SignUpManager.Request request) {
        return SignUpManager.Response.from(
            managerService.signUpManager(request.getEmail(),
                request.getPassword(),
                request.getIsPartner())
        );
    }
}
