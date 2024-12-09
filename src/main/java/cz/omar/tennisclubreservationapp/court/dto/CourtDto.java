package cz.omar.tennisclubreservationapp.court.dto;

import lombok.Data;

@Data
public class CourtDto {
    private Long id;
    private String name;
    private Long surfaceId;
}
