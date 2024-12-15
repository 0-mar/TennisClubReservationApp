package cz.omar.tennisclubreservationapp.token.storage;

import cz.omar.tennisclubreservationapp.token.business.Token;

import java.util.List;

public interface TokenRepository {
    Token create(TokenEntity tokenEntity);
    Token update(Token token);
    List<Token> getAllValidTokenByUser(Long id);
    Token getByToken(String token);
}
