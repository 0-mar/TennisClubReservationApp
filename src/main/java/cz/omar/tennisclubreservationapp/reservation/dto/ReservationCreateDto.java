package cz.omar.tennisclubreservationapp.reservation.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationCreateDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean doubles;
    private Long courtId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
