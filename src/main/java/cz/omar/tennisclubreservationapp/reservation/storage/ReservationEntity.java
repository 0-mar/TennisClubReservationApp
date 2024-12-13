package cz.omar.tennisclubreservationapp.reservation.storage;

import cz.omar.tennisclubreservationapp.common.storage.BaseEntity;
import cz.omar.tennisclubreservationapp.court.storage.CourtEntity;
import cz.omar.tennisclubreservationapp.customer.storage.CustomerEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ReservationEntity extends BaseEntity {
    @Column(name = "startTime", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "endTime", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "doubles", nullable = false)
    private boolean doubles;

    @ManyToOne
    private CourtEntity courtEntity;

    @ManyToOne
    private CustomerEntity customerEntity;
}
