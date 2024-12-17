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
    public TokenEntity delete(Long id) {
        return remove(id);
    }

    @Override
    public List<TokenEntity> getAllTokensByUser(Long userId) {
        return entityManager.createQuery(
                        "SELECT t FROM " + getClazz().getSimpleName() + " t WHERE t.userEntity.id = :userId AND t.deleted = false", getClazz())
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

    @Override
    public List<TokenEntity> getAllTokensByUser(Long userId, TokenType type) {
        return entityManager.createQuery(
                        "SELECT t FROM " + getClazz().getSimpleName() + " t WHERE t.userEntity.id = :userId AND t.type = :type AND t.deleted = false", getClazz())
                .setParameter("userId", userId)
                .setParameter("type", type)
                .getResultList();
    }
}
