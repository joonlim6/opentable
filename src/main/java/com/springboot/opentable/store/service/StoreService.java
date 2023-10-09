package com.springboot.opentable.store.service;

import com.springboot.opentable.exception.ErrorCode;
import com.springboot.opentable.exception.ManagerException;
import com.springboot.opentable.exception.StoreException;
import com.springboot.opentable.manager.domain.Manager;
import com.springboot.opentable.manager.repository.ManagerRepository;
import com.springboot.opentable.review.domain.Review;
import com.springboot.opentable.review.repository.ReviewRepository;
import com.springboot.opentable.store.domain.Store;
import com.springboot.opentable.store.dto.DeleteStore;
import com.springboot.opentable.store.dto.StoreDto;
import com.springboot.opentable.store.dto.StoreReviewDto;
import com.springboot.opentable.store.repository.StoreRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final ManagerRepository managerRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public StoreDto registerStore(Long managerId, String name, String location, String description) {
        Manager manager = managerRepository.findById(managerId)
            .orElseThrow(() -> new ManagerException(ErrorCode.NO_SUCH_MANAGER));

        if(!manager.getIsPartner()) {
            throw new ManagerException(ErrorCode.NOT_A_PARTNER);
        }

        Optional<Store> duplicate = storeRepository.findByManagerAndName(manager, name);

        if(duplicate.isPresent()) {
            throw new StoreException(ErrorCode.DUPLICATE_STORE);
        }

        Long newId = storeRepository.findFirstByOrderByIdDesc()
            .map(store -> store.getId() + 1)
            .orElse(1L);

        return StoreDto.fromEntity(
            storeRepository.save(Store.builder()
                .id(newId)
                .manager(manager)
                .name(name)
                .location(location)
                .description(description)
                .registeredAt(LocalDateTime.now())
                .build())
        );
    }

    @Transactional
    public List<StoreDto> getStoresByKeyword(String keyword) {
        List<Store> accounts = storeRepository.findByName(keyword);

        return accounts.stream()
            .map(StoreDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional
    public List<StoreReviewDto> getStoreReviews(Long storeId) {
        Store store = getStore(storeId);

        List<Review> reviews = reviewRepository.findByStore(store);

        return reviews.stream()
            .map(StoreReviewDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional
    public StoreDto updateStore(Long storeId, String name, String location, String description) {
        Store store = getStore(storeId);

        store.setName(name);
        store.setLocation(location);
        store.setDescription(description);

        return StoreDto.fromEntity(
            storeRepository.save(store)
        );
    }

    @Transactional
    public DeleteStore.Response deleteStore(Long storeId) {
        Store store = getStore(storeId);

        storeRepository.delete(store);

        return DeleteStore.Response.builder()
            .storeId(storeId)
            .deleted(true)
            .build();
    }

    public Store getStore(Long storeId) {
        return storeRepository.findById(storeId)
            .orElseThrow(() -> new StoreException(ErrorCode.NO_SUCH_STORE));
    }


}
