package cz.omar.tennisclubreservationapp.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReservationCreatedResultDto {
    private String phoneNumber;
    private LocalDateTime from;
    private LocalDateTime to;
    private float price;
}
