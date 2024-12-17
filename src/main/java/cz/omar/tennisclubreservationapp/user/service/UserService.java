package cz.omar.tennisclubreservationapp.user.service;

import cz.omar.tennisclubreservationapp.token.storage.TokenRepository;
import cz.omar.tennisclubreservationapp.user.business.User;
import cz.omar.tennisclubreservationapp.user.dto.UserCreateDto;
import cz.omar.tennisclubreservationapp.user.dto.UserDto;
import cz.omar.tennisclubreservationapp.user.dto.UserUpdateDto;
import cz.omar.tennisclubreservationapp.user.mapper.UserDtoToDatabaseMapper;
import cz.omar.tennisclubreservationapp.user.mapper.UserToDtoMapper;
import cz.omar.tennisclubreservationapp.user.storage.UserEntity;
import cz.omar.tennisclubreservationapp.user.storage.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserToDtoMapper userToDtoMapper;
    private final UserDtoToDatabaseMapper userDtoToDatabaseMapper;

    public UserDto create(UserCreateDto userCreateDto) {
        User userWithEmail = userRepository.getByEmail(userCreateDto.getEmail());
        if (userWithEmail != null) {
            throw new EmailAlreadyExistsException("Email is already used");
        }

        var password = passwordEncoder.encode(userCreateDto.getPassword());
        UserEntity userEntity = userDtoToDatabaseMapper.createDtoToEntity(userCreateDto, password);
        User user = userRepository.create(userEntity);
        return userToDtoMapper.userToUserDto(user);
    }

    public UserDto get(Long id) {
        return userToDtoMapper.userToUserDto(userRepository.get(id));
    }

    public List<UserDto> getAll() {
        return userRepository.getAll()
                .stream()
                .map(userToDtoMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto update(UserUpdateDto userUpdateDto) {
        var password = passwordEncoder.encode(userUpdateDto.getPassword());
        User oldUserData = userRepository.get(userUpdateDto.getId());
        User user = userToDtoMapper.updateDtoToUser(userUpdateDto, password, oldUserData.getEmail());
        return userToDtoMapper.userToUserDto(userRepository.update(user));
    }

    public UserDto delete(Long id) {
        var validUserTokens = tokenRepository.getAllTokensByUser(id);

        for (var token : validUserTokens) {
            tokenRepository.delete(token.getId());
        }

        return userToDtoMapper.userToUserDto(userRepository.delete(id));
    }
}
