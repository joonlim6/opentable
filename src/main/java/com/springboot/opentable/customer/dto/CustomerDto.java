package com.springboot.opentable.customer.dto;

import com.springboot.opentable.customer.domain.Customer;
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
public class CustomerDto {
    private Long userId;
    private String email;
    private LocalDateTime registeredAt;

    public static CustomerDto fromEntity(Customer customer) {
        return CustomerDto.builder()
            .userId(customer.getId())
            .email(customer.getEmail())
            .registeredAt(customer.getRegisteredAt())
            .build();
    }
}
