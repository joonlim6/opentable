package com.springboot.opentable.manager.repository;

import com.springboot.opentable.manager.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findFirstByOrderByIdDesc();
    Optional<Manager> findByEmail(String email);
}
