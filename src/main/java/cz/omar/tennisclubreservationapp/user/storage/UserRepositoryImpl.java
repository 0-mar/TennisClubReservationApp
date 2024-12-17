package cz.omar.tennisclubreservationapp.user.storage;

import cz.omar.tennisclubreservationapp.common.storage.RepositoryException;
import cz.omar.tennisclubreservationapp.user.business.User;
import cz.omar.tennisclubreservationapp.user.mapper.UserToDatabaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Transactional
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;
    private final UserToDatabaseMapper userToDatabaseMapper;

    @Override
    public User create(UserEntity userEntity) {
        return userToDatabaseMapper.entityToUser(userDao.create(userEntity));
    }

    @Override
    public User get(Long id) {
        UserEntity userEntity = userDao.get(id);
        if (userEntity == null) {
            throw new RepositoryException("User not found");
        }
        return userToDatabaseMapper.entityToUser(userEntity);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll().stream()
                .map(userToDatabaseMapper::entityToUser)
                .collect(Collectors.toList());
    }

    @Override
    public User update(User user) {
        UserEntity userEntity = userDao.update(userToDatabaseMapper.userToEntity(user));
        if (userEntity == null) {
            throw new RepositoryException("User not found");
        }
        return userToDatabaseMapper.entityToUser(userEntity);
    }

    @Override
    public User delete(Long id) {
        UserEntity userEntity = userDao.delete(id);
        if (userEntity == null) {
            throw new RepositoryException("User not found");
        }
        return userToDatabaseMapper.entityToUser(userEntity);
    }

    @Override
    public User getByEmail(String email) {
        UserEntity userEntity = userDao.getByEmail(email);
        return userToDatabaseMapper.entityToUser(userEntity);
    }
}
