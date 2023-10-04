package com.springboot.opentable.customer.controller;

import com.springboot.opentable.customer.dto.SignUpCustomer;
import com.springboot.opentable.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public SignUpCustomer.Response signUpCustomer(@RequestBody SignUpCustomer.Request request) {
        return SignUpCustomer.Response.from(
            customerService.signUpCustomer(
                request.getEmail(),
                request.getPassword()
            )
        );
    }
}
