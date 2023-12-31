package com.springboot.opentable.customer.repository;

import com.springboot.opentable.customer.domain.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findFirstByOrderByIdDesc();
    Optional<Customer> findByEmail(String email);
}
