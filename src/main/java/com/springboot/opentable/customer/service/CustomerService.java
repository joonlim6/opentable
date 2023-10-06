package com.springboot.opentable.customer.service;

import com.springboot.opentable.customer.domain.Customer;
import com.springboot.opentable.customer.dto.CustomerDto;
import com.springboot.opentable.customer.repository.CustomerRepository;
import com.springboot.opentable.exception.CustomerException;
import com.springboot.opentable.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerDto signUpCustomer(String email, String password) {
        Optional<Customer> duplicate = customerRepository.findByEmail(email);

        if(duplicate.isPresent()) {
            throw new CustomerException(ErrorCode.DUPLICATE_EMAIL_CUSTOMER);
        }

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
