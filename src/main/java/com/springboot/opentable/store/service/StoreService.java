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

        // 매니저가 파트너 등급일 때만 매장 등록 가능
        if(!manager.getIsPartner()) {
            throw new ManagerException(ErrorCode.NOT_A_PARTNER);
        }

        Optional<Store> duplicate = storeRepository.findByManagerAndName(manager, name);

        // 이미 등록한 매장은 등록 불가
        if(duplicate.isPresent()) {
            throw new StoreException(ErrorCode.DUPLICATE_STORE);
        }

        Long storeId = storeRepository.findFirstByOrderByIdDesc()
            .map(store -> store.getId() + 1)
            .orElse(1L);

        return StoreDto.fromEntity(
            storeRepository.save(Store.builder()
                .id(storeId)
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
        // 검색 키워드 (대,소문자 무관)가 매장 이름에 포함 됐을 시
        List<Store> stores = storeRepository.findByNameContainsIgnoreCase(keyword);

        return stores.stream()
            .map(StoreDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional
    public List<StoreReviewDto> getStoreReviews(Long storeId) {
        Store store = getStore(storeId);

        // 매장 별로 리뷰 열람 가능
        List<Review> reviews = reviewRepository.findByStore(store);

        return reviews.stream()
            .map(StoreReviewDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional
    public StoreDto updateStore(Long storeId, String name, String location, String description) {
        Store store = getStore(storeId);

        // 매장 명 변경, 위치 이전, 설명 수정
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
