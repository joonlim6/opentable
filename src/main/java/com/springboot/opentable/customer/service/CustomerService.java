package com.springboot.opentable.customer.service;

import com.springboot.opentable.customer.domain.Customer;
import com.springboot.opentable.customer.dto.CustomerDto;
import com.springboot.opentable.customer.dto.DeleteCustomer;
import com.springboot.opentable.customer.dto.DeleteCustomer.Response;
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

    @Transactional
    public CustomerDto updateCustomer(Long customerId, String password) {
        Customer customer = getCustomer(customerId);

        customer.setPassword(password);
        customerRepository.save(customer);

        return CustomerDto.fromEntity(customer);
    }

    @Transactional
    public DeleteCustomer.Response deleteCustomer(Long customerId) {
        Customer customer = getCustomer(customerId);

        customerRepository.delete(customer);

        return DeleteCustomer.Response.builder()
            .customerId(customerId)
            .deleted(true)
            .build();
    }

    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerException(ErrorCode.NO_SUCH_CUSTOMER));
    }

}
