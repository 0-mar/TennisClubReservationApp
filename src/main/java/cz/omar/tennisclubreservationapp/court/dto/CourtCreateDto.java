package cz.omar.tennisclubreservationapp.court.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourtCreateDto {
    @NotEmpty(message = "The name cannot be empty.")
    @NotNull(message = "The name cannot be null.")
    private String name;
    @NotNull(message = "Existing surface ID is required.")
    private Long surfaceId;
}
