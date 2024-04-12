package org.geekhub.crewcraft.user;

import org.geekhub.repository.user.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserDetailsManager extends UserDetailsService {
    void createUser(UserEntity user);

    void updateUser(UserEntity user);

    void deleteUser(String username);

    void changePassword(String oldPassword, String newPassword);

    boolean userExists(String username);
}
