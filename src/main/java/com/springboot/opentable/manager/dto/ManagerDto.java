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
    private String name;
    private Boolean isPartner;
    private LocalDateTime registeredAt;

    public static ManagerDto fromEntity(Manager manager) {
        return ManagerDto.builder()
            .userId(manager.getId())
            .email(manager.getEmail())
            .name(manager.getName())
            .isPartner(manager.getIsPartner())
            .registeredAt(manager.getRegisteredAt())
            .build();
    }
}
