package cz.omar.tennisclubreservationapp.reservation.storage;

import cz.omar.tennisclubreservationapp.common.storage.RepositoryException;
import cz.omar.tennisclubreservationapp.reservation.business.Reservation;
import cz.omar.tennisclubreservationapp.reservation.mapper.ReservationToDatabaseMapper;
import org.springframework.stereotype.Repository;

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

    @Override
    public Reservation create(ReservationEntity reservationEntity) {
        if (reservationDao.intervalOverlaps(reservationEntity.getStartTime(), reservationEntity.getEndTime())) {
            throw new RepositoryException("Time slot already reserved");
        }
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
