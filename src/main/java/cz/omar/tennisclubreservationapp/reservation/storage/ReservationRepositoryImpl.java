package cz.omar.tennisclubreservationapp.reservation.storage;

import cz.omar.tennisclubreservationapp.common.storage.RepositoryException;
import cz.omar.tennisclubreservationapp.reservation.business.Reservation;
import cz.omar.tennisclubreservationapp.reservation.mapper.ReservationToDatabaseMapper;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationDao reservationDao;
    private final ReservationToDatabaseMapper reservationToDatabaseMapper;

    public ReservationRepositoryImpl(ReservationDao reservationDao, ReservationToDatabaseMapper reservationToDatabaseMapper) {
        this.reservationDao = reservationDao;
        this.reservationToDatabaseMapper = reservationToDatabaseMapper;
    }


    /**
     * Validates the time interval for a reservation. Ensures that the specified time interval adheres
     * to predefined business rules, including no overlaps with existing reservations, time restrictions,
     * and duration constraints. If any rule is violated, a {@link RepositoryException} is thrown.
     *
     * @param startTime the start time of the reservation
     * @param endTime   the end time of the reservation
     * @throws RepositoryException if the reservation time interval violates any constraints
     */
    private void checkTimeInterval(LocalDateTime startTime, LocalDateTime endTime) {
        if (reservationDao.intervalOverlaps(startTime, endTime)) {
            throw new RepositoryException("Time slot already reserved");
        }

        if (startTime.getHour() < 6) {
            throw new RepositoryException("Reservations before 6 are not allowed");
        }

        if (endTime.getHour() == 22 && endTime.getMinute() > 0) {
            throw new RepositoryException("Reservations after 22 are not allowed");
        }

        if ((startTime.getYear() != endTime.getYear()) &&
                (startTime.getDayOfYear() != endTime.getDayOfYear())) {
            throw new RepositoryException("Reservations can be only made within the same day");
        }

        Duration duration = Duration.between(startTime, endTime);
        if (duration.toMinutes() < 30 || duration.toMinutes() > 240){
            throw new RepositoryException("One reservation can be minimum 30 mins and maximum 240 mins");
        }
    }

    @Override
    public Reservation create(ReservationEntity reservationEntity) {
        checkTimeInterval(reservationEntity.getStartTime(), reservationEntity.getEndTime());
        return reservationToDatabaseMapper.entityToReservation(reservationDao.create(reservationEntity));
    }

    @Override
    public Reservation get(Long id) {
        ReservationEntity reservationEntity = reservationDao.get(id);
        if (reservationEntity == null) {
            throw new RepositoryException("Reservation " + id + " not found");
        }
        return reservationToDatabaseMapper.entityToReservation(reservationEntity);
    }

    @Override
    public List<Reservation> getAll() {
        return reservationDao.getAll()
                .stream()
                .map(reservationToDatabaseMapper::entityToReservation)
                .collect(Collectors.toList());
    }

    @Override
    public Reservation delete(Long id) {
        ReservationEntity reservationEntity = reservationDao.get(id);
        if (reservationEntity == null) {
            throw new RepositoryException("Reservation " + id + " not found");
        }
        return reservationToDatabaseMapper.entityToReservation(reservationDao.delete(id));
    }

    @Override
    public Reservation update(Reservation reservation) {
        ReservationEntity reservationEntity = reservationDao.get(reservation.getId());
        if (reservationEntity == null) {
            throw new RepositoryException("Reservation " + reservation.getId() + " not found");
        }

        checkTimeInterval(reservation.getStartTime(), reservation.getEndTime());

        ReservationEntity updatedEntity = reservationDao.update(reservationToDatabaseMapper.reservationToEntity(reservation));
        return reservationToDatabaseMapper.entityToReservation(updatedEntity);
    }

    @Override
    public List<Reservation> getReservationsByCourt(Long courtId) {
        return reservationDao.getReservationsByCourt(courtId).stream()
                .map(reservationToDatabaseMapper::entityToReservation)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> getReservationsByPhoneNumber(String phoneNumber, boolean onlyFuture) {
        return reservationDao.getReservationsByPhoneNumber(phoneNumber, onlyFuture).stream()
                .map(reservationToDatabaseMapper::entityToReservation)
                .collect(Collectors.toList());
    }
}
