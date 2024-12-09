package cz.omar.tennisclubreservationapp.court.storage;

import cz.omar.tennisclubreservationapp.common.storage.BaseEntity;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class CourtEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    private SurfaceEntity surfaceEntity;
}
