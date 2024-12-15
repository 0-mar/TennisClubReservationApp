package cz.omar.tennisclubreservationapp.user.storage;

import cz.omar.tennisclubreservationapp.common.storage.AbstractDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl extends AbstractDao<UserEntity> implements UserDao {
    public UserDaoImpl() {
        super(UserEntity.class);
    }

    @Override
    public UserEntity create(UserEntity userEntity) {
        return save(userEntity);
    }

    @Override
    public UserEntity get(Long id) {
        return findById(id);
    }

    @Override
    public List<UserEntity> getAll() {
        return findAll();
    }

    @Override
    public UserEntity delete(Long id) {
        return remove(id);
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        return merge(userEntity);
    }

    @Override
    public UserEntity getByEmail(String email) {
        return entityManager.createQuery(
                        "SELECT u FROM " + getClazz().getSimpleName() + " u WHERE u.email = :email AND u.deleted = false", getClazz())
                .setParameter("email", email)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}
