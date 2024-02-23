package org.geekhub.service;

import org.geekhub.exception.UserException;
import org.geekhub.model.User;
import org.geekhub.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(long userId) {
        return userRepository.getUser(userId)
            .orElseThrow(() -> new UserException("User with id " + userId + " not found"));
    }

    public boolean isUserExist(long userId) {
        return userRepository.isUserExists(userId);
    }
}
