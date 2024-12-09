package cz.omar.tennisclubreservationapp.surface.business;

import lombok.Data;

@Data
public class Surface {
    private Long id;
    private String name;
    private float rentPerMinute;
}
