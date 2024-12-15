package cz.omar.tennisclubreservationapp.token.mapper;

import cz.omar.tennisclubreservationapp.token.business.Token;
import cz.omar.tennisclubreservationapp.token.storage.TokenEntity;
import cz.omar.tennisclubreservationapp.user.mapper.UserToDatabaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserToDatabaseMapper.class})
public interface TokenToDatabaseMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "revoked", target = "revoked")
    @Mapping(source = "expired", target = "expired")
    @Mapping(source = "userEntity", target = "user")
    Token entityToToken(TokenEntity tokenEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "revoked", target = "revoked")
    @Mapping(source = "expired", target = "expired")
    @Mapping(source = "user", target = "userEntity")
    TokenEntity tokenToEntity(Token token);
}
