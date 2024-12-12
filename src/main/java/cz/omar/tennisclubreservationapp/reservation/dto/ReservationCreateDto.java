package cz.omar.tennisclubreservationapp.reservation.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationCreateDto {
    private LocalDateTime from;
    private LocalDateTime to;
    private boolean doubles;
    private Long courtId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
