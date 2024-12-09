package cz.omar.tennisclubreservationapp.common.storage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class AbstractDao<T extends BaseEntity> {
    @PersistenceContext
    protected EntityManager entityManager;

    private Class<T> clazz;

    protected final void setClazz(final Class<T> clazzToSet) {
        clazz = clazzToSet;
    }

    protected T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    protected T findById(Long id) {
        return entityManager.createQuery(
                        "SELECT e FROM " + clazz.getSimpleName() + " e WHERE e.id = :id AND e.deleted = false", clazz)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    protected List<T> findAll() {
        return entityManager.createQuery(
                        "SELECT e FROM " + clazz.getSimpleName() + " e WHERE e.deleted = false", clazz)
                .getResultList();
    }

    protected T remove(Long id) {
        T entity = findById(id);
        if (entity == null) {
            return null;
        }

        entity.setDeleted(true);
        entityManager.merge(entity);

        return entity;
    }

    protected T merge(T entity) {
        entityManager.merge(entity);

        return entity;
    }
}
