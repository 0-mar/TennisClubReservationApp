package cz.omar.tennisclubreservationapp.token.storage;

import cz.omar.tennisclubreservationapp.common.storage.AbstractDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenDaoImpl extends AbstractDao<TokenEntity> implements TokenDao {
    public TokenDaoImpl() {
        super(TokenEntity.class);
    }

    @Override
    public TokenEntity create(TokenEntity entity) {
        return save(entity);
    }

    @Override
    public TokenEntity update(TokenEntity entity) {
        return merge(entity);
    }

    @Override
    public List<TokenEntity> getAllValidTokenByUser(Long userId) {
        return entityManager.createQuery(
                        "SELECT t FROM " + getClazz().getSimpleName() + " t WHERE t.userEntity.id = :userId AND (t.expired = false OR t.revoked = false) AND t.deleted = false", getClazz())
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public TokenEntity getByToken(String token) {
        return entityManager.createQuery("SELECT t FROM " + getClazz().getSimpleName() + " t WHERE t.token = :token AND t.deleted = false", getClazz())
                .setParameter("token", token)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}
