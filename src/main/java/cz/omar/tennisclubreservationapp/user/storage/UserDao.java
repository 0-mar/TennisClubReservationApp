package cz.omar.tennisclubreservationapp.user.storage;

import java.util.List;

public interface UserDao {
    UserEntity create(UserEntity userEntity);
    UserEntity get(Long id);
    List<UserEntity> getAll();
    UserEntity delete(Long id);
    UserEntity update(UserEntity userEntity);
    UserEntity getByEmail(String email);
}
