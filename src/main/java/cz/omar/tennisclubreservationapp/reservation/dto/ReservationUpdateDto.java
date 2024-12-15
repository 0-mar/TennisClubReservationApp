package cz.omar.tennisclubreservationapp.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationUpdateDto {
    @NotNull
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean doubles;
    private Long courtId;
    private Long customerId;
}
