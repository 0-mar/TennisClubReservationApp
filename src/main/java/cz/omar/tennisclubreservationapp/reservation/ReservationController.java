package cz.omar.tennisclubreservationapp.reservation;

import cz.omar.tennisclubreservationapp.reservation.dto.ReservationCreateDto;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationCreatedResultDto;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationDto;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationUpdateDto;
import cz.omar.tennisclubreservationapp.reservation.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ReservationCreatedResultDto createReservation(@Valid @RequestBody ReservationCreateDto reservationCreateDto) {
        return reservationService.create(reservationCreateDto);
    }

    @GetMapping("/{reservationId}")
    public ReservationDto getReservation(@PathVariable Long reservationId) {
        return reservationService.get(reservationId);
    }

    @GetMapping
    public List<ReservationDto> getReservations(@RequestParam(required = false) Long courtId,
                                                @RequestParam(required = false) String phoneNumber,
                                                @RequestParam(defaultValue = "false") Boolean onlyFuture) {
        if (courtId != null) {
            return reservationService.getReservationsByCourt(courtId);
        } else if (phoneNumber != null) {
            return reservationService.getReservationsByPhoneNumber(phoneNumber, onlyFuture);
        }

        return reservationService.getAll();
    }

    @PutMapping("/{reservationId}")
    public ReservationDto updateReservation(@PathVariable Long reservationId, @Valid @RequestBody ReservationUpdateDto reservationUpdateDto) {
        reservationUpdateDto.setId(reservationId);
        return reservationService.update(reservationUpdateDto);
    }

    @DeleteMapping("/{reservationId}")
    public ReservationDto deleteReservation(@PathVariable Long reservationId) {
        return reservationService.delete(reservationId);
    }
}
