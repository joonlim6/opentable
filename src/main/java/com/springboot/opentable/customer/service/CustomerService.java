package com.springboot.opentable.customer.service;

import com.springboot.opentable.customer.domain.Customer;
import com.springboot.opentable.customer.dto.CustomerDto;
import com.springboot.opentable.customer.repository.CustomerRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerDto signUpCustomer(String email, String password) {
        Long newId = customerRepository.findFirstByOrderByIdDesc()
            .map(customer -> customer.getId() + 1)
            .orElse(1L);

        return CustomerDto.fromEntity(
            customerRepository.save(Customer.builder()
                .id(newId)
                .email(email)
                .password(password)
                .registeredAt(LocalDateTime.now())
                .build())
        );
    }
}
