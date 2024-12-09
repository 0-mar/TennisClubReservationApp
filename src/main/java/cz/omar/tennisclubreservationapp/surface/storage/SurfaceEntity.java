package cz.omar.tennisclubreservationapp.surface.storage;

import cz.omar.tennisclubreservationapp.common.storage.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class SurfaceEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "rentPerMinute", nullable = false)
    private float rentPerMinute;
}
