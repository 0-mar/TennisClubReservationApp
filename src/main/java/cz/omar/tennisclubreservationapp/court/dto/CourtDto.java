package cz.omar.tennisclubreservationapp.court.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtDto {
    private Long id;
    private String name;
    private Long surfaceId;
}
