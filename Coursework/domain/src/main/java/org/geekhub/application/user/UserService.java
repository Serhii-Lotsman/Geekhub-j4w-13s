package org.geekhub.application.user;

import org.geekhub.application.exception.UniqueUserException;
import org.geekhub.application.exception.UserException;
import org.geekhub.application.exception.UserExistException;
import org.geekhub.application.exception.ValidationException;
import org.geekhub.application.user.model.UserEntity;
import org.geekhub.application.user.model.UserRole;
import org.geekhub.application.validation.UserValidation;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.geekhub.application.user.UserRoleRepository.SUPER_ADMIN;

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
        if (!userRepository.existsByUserEmail(findById(userId).getEmail())) {
            throw new UserException("Cannot find user");
        }

        if (roleId == 3 || validateUser(userId)) {
            throw new UniqueUserException("Cannot assign this role to the user");
        }
        userRoleRepository.updateRole(userId, roleId);
    }

    public void updateUser(UserEntity userEntity) {
        if (UserValidation.isInvalidEmail(userEntity.getEmail())) {
            throw new ValidationException("Invalid email!");
        }

        if (userRepository.existsByUserEmail(userEntity.getEmail())) {
            throw new UserExistException("Email is taken!");
        }

        if (validateUser(userEntity.getId())) {
            throw new UniqueUserException("Cannot update this user");
        }
        userRepository.updateUser(userEntity);
    }

    public List<UserEntity> getUsers() {
        return userRepository.findUsers();
    }

    public UserEntity findById(long userId) {
        return userRepository.findUserById(userId)
            .orElseThrow(() -> new UserException("Cannot find user"));
    }

    public void deleteUser(long userId) {
        if (!userRepository.existsByUserEmail(findById(userId).getEmail())) {
            throw new UserException("Cannot find user");
        }

        if (validateUser(userId)) {
            throw new UniqueUserException("Cannot delete this user");
        }
        userRepository.deleteUser(userId);
    }

    public int getRoleIdByName(String roleName) {
        return userRoleRepository.findRole(roleName).stream().map(UserRole::getId).findFirst()
            .orElseThrow(() -> new UserException("Role not found"));
    }

    public boolean validatePassword(String password) {
        return UserValidation.isValidPassword(password);
    }

    private boolean validateUser(Long userId) {
        return userRoleRepository.getRoles(userId)
            .stream()
            .anyMatch(role -> role.getRole().equals(SUPER_ADMIN));
    }
}
