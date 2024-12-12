package cz.omar.tennisclubreservationapp.reservation.storage;

import cz.omar.tennisclubreservationapp.reservation.business.Reservation;

import java.util.List;

public interface ReservationRepository {
    Reservation create(ReservationEntity reservationEntity);
    Reservation get(Long id);
    List<Reservation> getAll();
    Reservation delete(Long id);
    Reservation update(Reservation reservation);
    List<Reservation> getReservationsByCourt(Long courtId);
    List<Reservation> getReservationsByPhoneNumber(String phoneNumber, boolean onlyFuture);
}
