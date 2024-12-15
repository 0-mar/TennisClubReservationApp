package cz.omar.tennisclubreservationapp.court.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtUpdateDto {
    @NotNull(message = "The court ID to be updated is required.")
    private Long id;
    private String name;
    private Long surfaceId;
}
