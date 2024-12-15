package cz.omar.tennisclubreservationapp.token.storage;

import java.util.List;

public interface TokenDao {
    TokenEntity create(TokenEntity entity);
    TokenEntity update(TokenEntity entity);
    List<TokenEntity> getAllValidTokenByUser(Long id);
    TokenEntity getByToken(String token);
}
