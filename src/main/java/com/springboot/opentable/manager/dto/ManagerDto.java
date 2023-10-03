package com.springboot.opentable.manager.dto;

import com.springboot.opentable.manager.domain.Manager;
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
public class ManagerDto {
    private Long userId;
    private String email;
    private Boolean isPartner;
    private LocalDateTime registeredAt;
    private LocalDateTime unRegisteredAt;

    public static ManagerDto fromEntity(Manager manager) {
        return ManagerDto.builder()
            .userId(manager.getId())
            .email(manager.getEmail())
            .isPartner(manager.getIsPartner())
            .registeredAt(manager.getRegisteredAt())
            .unRegisteredAt(manager.getUnRegisteredAt())
            .build();
    }
}
