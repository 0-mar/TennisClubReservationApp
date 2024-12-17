package cz.omar.tennisclubreservationapp.user.mapper;

import cz.omar.tennisclubreservationapp.user.dto.UserCreateDto;
import cz.omar.tennisclubreservationapp.user.storage.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoToDatabaseMapper {

    @Mapping(ignore = true, target = "id")
    @Mapping(source = "userCreateDto.email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "userCreateDto.role", target = "role")
    @Mapping(constant = "false", target = "deleted")
    UserEntity createDtoToEntity(UserCreateDto userCreateDto, String password);
}
