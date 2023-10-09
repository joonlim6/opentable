package com.springboot.opentable.customer.controller;

import com.springboot.opentable.customer.dto.DeleteCustomer;
import com.springboot.opentable.customer.dto.SignUpCustomer;
import com.springboot.opentable.customer.dto.UpdateCustomer;
import com.springboot.opentable.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PatchMapping("/update")
    public UpdateCustomer.Response updateCustomer(@RequestBody UpdateCustomer.Request request) {
        return UpdateCustomer.Response.from(
            customerService.updateCustomer(
                request.getCustomerId(),
                request.getPassword()
            )
        );
    }

    @DeleteMapping("/delete/{customer_id}")
    public DeleteCustomer.Response deleteCustomer(@PathVariable Long customer_id) {
        return customerService.deleteCustomer(customer_id);
    }
}
