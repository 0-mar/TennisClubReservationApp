package cz.omar.tennisclubreservationapp.user.storage;

import cz.omar.tennisclubreservationapp.user.business.User;

import java.util.List;

public interface UserRepository {
    User create(UserEntity userEntity);
    User get(Long id);
    List<User> getAll();
    User update(User user);
    User delete(Long id);
    User getByEmail(String email);

}
