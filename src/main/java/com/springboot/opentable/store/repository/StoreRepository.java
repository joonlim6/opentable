package com.springboot.opentable.store.repository;

import com.springboot.opentable.store.domain.Store;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findFirstByOrderByIdDesc();
    List<Store> findByName(String keyword);
}
