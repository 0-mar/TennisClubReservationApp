package cz.omar.tennisclubreservationapp.token.storage;

import java.util.List;

public interface TokenDao {
    TokenEntity create(TokenEntity entity);
    TokenEntity update(TokenEntity entity);
    TokenEntity delete(Long id);
    List<TokenEntity> getAllTokensByUser(Long id);
    TokenEntity getByToken(String token);
    List<TokenEntity> getAllTokensByUser(Long id, TokenType type);
}
