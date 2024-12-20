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
    /**
     * Retrieves a list of reservations for a customer via their phone number.
     * Optionally, filters the results to include only future reservations.
     *
     * @param phoneNumber the phone number of the customer whose reservations should be retrieved.
     * @param onlyFuture when true, filters the reservations to include only those with a start time in the future.
     * @return a list of reservation entities matching the provided phone number and filter criteria.
     */
    List<ReservationEntity> getReservationsByPhoneNumber(String phoneNumber, boolean onlyFuture);
    /**
     * Determines if the specified time interval overlaps with any existing reservations.
     * An overlap occurs if any part of the provided interval intersects with
     * another reservation's interval.
     *
     * @param startTime the start time of the interval to check
     * @param endTime the end time of the interval to check
     * @return true if the interval overlaps with an existing reservation, false otherwise
     */
    boolean intervalOverlaps(LocalDateTime startTime, LocalDateTime endTime);

}
