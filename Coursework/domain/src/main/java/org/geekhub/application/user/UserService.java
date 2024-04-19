package org.geekhub.application.user;

import org.geekhub.application.exception.UserException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    public UserService(
        UserRoleRepository userRoleRepository,
        UserRepository userRepository
    ) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    public void updateRole(long userId, long roleId) {
        if (roleId == 3) {
            throw new UserException("Cannot assign this role to the user");
        }
        userRoleRepository.updateRole(userId, roleId);
    }

    public void updateUser(UserEntity userEntity) {
        userRepository.updateUser(userEntity);
    }

    public UserEntity findById(long id) {
        return userRepository.findUserById(id)
            .orElseThrow(() -> new UserException("Cannot find user by id"));
    }

    public long deleteUser(long id) {
        return userRepository.deleteUser(id);
    }
}
