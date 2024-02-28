package org.geekhub.repository;

import org.geekhub.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> getUser(long userId);

    default boolean isUserExists(long userId) {
        return getUser(userId).isPresent();
    }
    List<User> getUsers();
}
