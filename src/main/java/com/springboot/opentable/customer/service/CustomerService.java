package com.springboot.opentable.customer.service;

import com.springboot.opentable.customer.domain.Customer;
import com.springboot.opentable.customer.dto.CustomerDto;
import com.springboot.opentable.customer.dto.DeleteCustomer;
import com.springboot.opentable.customer.repository.CustomerRepository;
import com.springboot.opentable.exception.CustomerException;
import com.springboot.opentable.exception.ErrorCode;
import com.springboot.opentable.exception.ManagerException;
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

        // 이메일 중복 불가
        if(duplicate.isPresent()) {
            throw new CustomerException(ErrorCode.DUPLICATE_EMAIL_CUSTOMER);
        }

        Long customerId = customerRepository.findFirstByOrderByIdDesc()
            .map(customer -> customer.getId() + 1)
            .orElse(1L);

        return CustomerDto.fromEntity(
            customerRepository.save(Customer.builder()
                .id(customerId)
                .email(email)
                .password(password)
                .registeredAt(LocalDateTime.now())
                .build())
        );
    }

    @Transactional
    public CustomerDto updateCustomer(Long customerId, String password) {
        Customer customer = getCustomer(customerId);

        // 비밀 번호만 수정 가능
        customer.setPassword(password);
        customerRepository.save(customer);

        return CustomerDto.fromEntity(customer);
    }

    @Transactional
    public DeleteCustomer.Response deleteCustomer(Long customerId, String email, String password) {
        Customer customer = getCustomer(customerId);

        // 이메일, 비밀 번호 불일치 시 회원 탈퇴 불가능
        if(!customer.getEmail().equals(email) || !customer.getPassword().equals(password)) {
            throw new CustomerException(ErrorCode.WRONG_EMAIL_OR_PASSWORD);
        }

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
