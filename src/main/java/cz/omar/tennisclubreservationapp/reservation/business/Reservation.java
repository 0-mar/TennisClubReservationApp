package cz.omar.tennisclubreservationapp.reservation.business;

import cz.omar.tennisclubreservationapp.court.business.Court;
import cz.omar.tennisclubreservationapp.customer.business.Customer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reservation {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean doubles;
    private Court court;
    private Customer customer;
}
