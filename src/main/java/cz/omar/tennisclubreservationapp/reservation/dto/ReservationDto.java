package cz.omar.tennisclubreservationapp.reservation.dto;

import java.time.LocalDateTime;

public class ReservationDto {
    private Long id;
    private LocalDateTime from;
    private LocalDateTime to;
    private boolean doubles;
    private Long courtId;
    private Long customerId;
}
