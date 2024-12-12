package cz.omar.tennisclubreservationapp.court.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourtUpdateDto {
    @NotNull(message = "The court ID to be updated is required.")
    private Long id;
    private String name;
    private Long surfaceId;
}
