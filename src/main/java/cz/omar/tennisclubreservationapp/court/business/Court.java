package cz.omar.tennisclubreservationapp.court.business;

import cz.omar.tennisclubreservationapp.surface.business.Surface;
import lombok.Data;

@Data
public class Court {
    private Long id;
    private String name;
    private Surface surface;
}
