package cz.omar.tennisclubreservationapp.user.mapper;

import cz.omar.tennisclubreservationapp.user.business.User;
import cz.omar.tennisclubreservationapp.user.dto.UserDto;
import cz.omar.tennisclubreservationapp.user.dto.UserUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserToDtoMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    UserDto userToUserDto(User user);

    @Mapping(source = "userUpdateDto.id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "userUpdateDto.role", target = "role")
    User updateDtoToUser(UserUpdateDto userUpdateDto, String password, String email);
}
