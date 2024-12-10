package cz.omar.tennisclubreservationapp.reservation.storage;

import cz.omar.tennisclubreservationapp.common.storage.AbstractDao;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReservationDaoImpl extends AbstractDao<ReservationEntity> implements ReservationDao {

    public ReservationDaoImpl() {
        super(ReservationEntity.class);
    }

    @Override
    public ReservationEntity create(ReservationEntity reservationEntity) {
        return save(reservationEntity);
    }

    @Override
    public ReservationEntity get(Long id) {
        return findById(id);
    }

    @Override
    public List<ReservationEntity> getAll() {
        return findAll();
    }

    @Override
    public ReservationEntity delete(Long id) {
        return remove(id);
    }

    @Override
    public ReservationEntity update(ReservationEntity reservationEntity) {
        return merge(reservationEntity);
    }

    @Override
    public List<ReservationEntity> getReservationsByCourt(Long courtId) {
        return entityManager.createQuery(
                "SELECT r FROM " + getClazz().getSimpleName() + " r WHERE r.courtEntity.id = :courtId AND r.deleted = false ORDER BY r.from ASC", getClazz())
                .setParameter("courtId", courtId)
                .getResultList();
    }

    @Override
    public List<ReservationEntity> getReservationsByPhoneNumber(String phoneNumber, boolean onlyFuture) {
        StringBuilder queryString = new StringBuilder("SELECT r FROM " + getClazz().getSimpleName() + " r WHERE r.customerEntity.phoneNumber = :phoneNumber AND r.deleted = false");

        if (onlyFuture) {
            queryString.append(" AND r.from > :now");
        }

        queryString.append(" ORDER BY r.from ASC");

        var query = entityManager.createQuery(queryString.toString(), getClazz())
                .setParameter("phoneNumber", phoneNumber);

        if (onlyFuture) {
            query.setParameter("now", LocalDateTime.now());
        }

        return query.getResultList();
    }
}
