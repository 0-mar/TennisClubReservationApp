package cz.omar.tennisclubreservationapp.user.storage;

import cz.omar.tennisclubreservationapp.user.business.User;
import cz.omar.tennisclubreservationapp.user.mapper.UserToDatabaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public User getByEmail(String email) {
        return userToDatabaseMapper.entityToUser(userDao.getByEmail(email));
    }
}
