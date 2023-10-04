package com.springboot.opentable.store.dto;

import com.springboot.opentable.store.domain.Store;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {
    private Long storeId;
    private Long managerId;
    private String name;
    private String location;
    private String description;
    private LocalDateTime registeredAt;

    public static StoreDto fromEntity(Store store) {
        return StoreDto.builder()
            .storeId(store.getId())
            .managerId(store.getManager().getId())
            .name(store.getName())
            .location(store.getLocation())
            .description(store.getDescription())
            .registeredAt(store.getRegisteredAt())
            .build();
    }
}
