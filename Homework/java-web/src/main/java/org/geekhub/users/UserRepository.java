package org.geekhub.users;

import java.util.Optional;

public interface UserRepository {

    Optional<User> getUser(int userId);

    default boolean isUserExists(int userId) {
        return getUser(userId).isPresent();
    }
}
