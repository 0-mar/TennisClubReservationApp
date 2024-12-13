package cz.omar.tennisclubreservationapp.reservation.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationUpdateDto {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean doubles;
    private Long courtId;
    private Long customerId;
}
