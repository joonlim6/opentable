package com.springboot.opentable.review.domain;

import com.springboot.opentable.customer.domain.Customer;
import com.springboot.opentable.reservation.domain.Reservation;
import com.springboot.opentable.store.domain.Store;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Reservation reservation;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Store store;

    private String reviewText;

    private LocalDateTime reviewedAt;
}
