package cz.omar.tennisclubreservationapp.reservation.storage;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationDao {
    ReservationEntity create(ReservationEntity reservationEntity);
    ReservationEntity get(Long id);
    List<ReservationEntity> getAll();
    ReservationEntity delete(Long id);
    ReservationEntity update(ReservationEntity reservationEntity);
    List<ReservationEntity> getReservationsByCourt(Long courtId);
    List<ReservationEntity> getReservationsByPhoneNumber(String phoneNumber, boolean onlyFuture);
    boolean intervalOverlaps(LocalDateTime startTime, LocalDateTime endTime);

}
