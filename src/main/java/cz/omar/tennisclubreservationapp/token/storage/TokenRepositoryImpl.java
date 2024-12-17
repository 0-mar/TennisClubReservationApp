package cz.omar.tennisclubreservationapp.token.storage;

import cz.omar.tennisclubreservationapp.common.storage.RepositoryException;
import cz.omar.tennisclubreservationapp.token.business.Token;
import cz.omar.tennisclubreservationapp.token.mapper.TokenToDatabaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Transactional
public class TokenRepositoryImpl implements TokenRepository {

    private final TokenDao tokenDao;
    private final TokenToDatabaseMapper tokenToDatabaseMapper;

    @Override
    public Token create(TokenEntity tokenEntity) {
        return tokenToDatabaseMapper.entityToToken(tokenDao.create(tokenEntity));
    }

    @Override
    public Token update(Token token) {
        TokenEntity tokenEntity = tokenDao.update(tokenToDatabaseMapper.tokenToEntity(token));
        if (tokenEntity == null) {
            throw new RepositoryException("Token not found");
        }

        return tokenToDatabaseMapper.entityToToken(tokenEntity);
    }

    @Override
    public Token delete(Long id) {
        TokenEntity deleted = tokenDao.delete(id);
        if (deleted == null) {
            throw new RepositoryException("Token not found");
        }

        return tokenToDatabaseMapper.entityToToken(deleted);
    }

    @Override
    public List<Token> getAllTokensByUser(Long id) {
        return tokenDao.getAllTokensByUser(id).stream()
                .map(tokenToDatabaseMapper::entityToToken)
                .collect(Collectors.toList());
    }

    @Override
    public Token getByToken(String token) {
        TokenEntity tokenEntity = tokenDao.getByToken(token);
        if (tokenEntity == null) {
            throw new RepositoryException("Token not found");
        }
        return tokenToDatabaseMapper.entityToToken(tokenEntity);
    }

    @Override
    public List<Token> getAllTokensByUser(Long id, TokenType type) {
        return tokenDao.getAllTokensByUser(id, type).stream()
                .map(tokenToDatabaseMapper::entityToToken)
                .collect(Collectors.toList());
    }
}
