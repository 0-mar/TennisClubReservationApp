package cz.omar.tennisclubreservationapp.court.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CourtUpdateDto {
    @NotEmpty(message = "The court ID to be updated is required.")
    private Long id;
    private String name;
    private Long surfaceId;
}
