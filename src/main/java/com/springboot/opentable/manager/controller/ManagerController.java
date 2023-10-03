package com.springboot.opentable.manager.controller;

import com.springboot.opentable.manager.dto.CreateAccount;
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
    public CreateAccount.Response createAccount(@RequestBody CreateAccount.Request request) {
        return CreateAccount.Response.from(
            managerService.createAccount(request.getEmail(),
                request.getPassword(),
                request.getIsPartner())
        );
    }
}
