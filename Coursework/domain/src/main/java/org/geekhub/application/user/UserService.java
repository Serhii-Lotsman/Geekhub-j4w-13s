package org.geekhub.application.user;

import org.geekhub.application.exception.AuthException;
import org.geekhub.application.validation.UserValidation;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserValidation userValidation;

    public UserService(
        UserRepository userRepository,
        UserRoleRepository userRoleRepository,
        UserValidation userValidation
    ) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userValidation = userValidation;
    }

    public int createUser(UserEntity userEntity) {
        if (!userValidation.isValidEmail(userEntity.getEmail())
            || isEmailExist(userEntity.getEmail())
            || userEntity.getEmail().trim().length() > 30) {
            throw new AuthException("Invalid email! Must be at range 3 - 30");
        }
        return userRepository.saveUser(userEntity);
    }

    public boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    public void setUserRole(int userId, int roleId) {
        userRoleRepository.assignRole(userId, roleId);
    }

    public UserRole getRoleByName(String roleName) {
        return userRoleRepository.findRole(roleName);
    }
}
