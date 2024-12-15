package cz.omar.tennisclubreservationapp.token.storage;

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
        return tokenToDatabaseMapper.entityToToken(tokenDao.update(tokenToDatabaseMapper.tokenToEntity(token)));
    }

    @Override
    public List<Token> getAllValidTokenByUser(Long id) {
        return tokenDao.getAllValidTokenByUser(id).stream()
                .map(tokenToDatabaseMapper::entityToToken)
                .collect(Collectors.toList());
    }

    @Override
    public Token getByToken(String token) {
        return tokenToDatabaseMapper.entityToToken(tokenDao.getByToken(token));
    }
}
