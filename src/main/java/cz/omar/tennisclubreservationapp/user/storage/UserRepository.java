package cz.omar.tennisclubreservationapp.user.storage;

import cz.omar.tennisclubreservationapp.user.business.User;

public interface UserRepository {
    User create(UserEntity userEntity);
    User getByEmail(String email);

}
