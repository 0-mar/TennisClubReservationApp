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
    @Mapping(source = "userEntity", target = "user")
    @Mapping(source = "type", target = "type")
    Token entityToToken(TokenEntity tokenEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "user", target = "userEntity")
    @Mapping(source = "type", target = "type")
    @Mapping(constant = "false", target = "deleted")
    TokenEntity tokenToEntity(Token token);
}
