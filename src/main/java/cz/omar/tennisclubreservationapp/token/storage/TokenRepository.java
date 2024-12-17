package cz.omar.tennisclubreservationapp.token.storage;

import cz.omar.tennisclubreservationapp.token.business.Token;

import java.util.List;

public interface TokenRepository {
    Token create(TokenEntity tokenEntity);
    Token update(Token token);
    Token delete(Long id);
    List<Token> getAllTokensByUser(Long id);
    Token getByToken(String token);
    List<Token> getAllTokensByUser(Long id, TokenType type);
}
