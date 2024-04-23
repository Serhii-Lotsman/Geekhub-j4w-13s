package org.geekhub.application.user;

import org.geekhub.application.exception.UserException;
import org.geekhub.application.exception.ValidationException;
import org.geekhub.application.user.model.UserEntity;
import org.geekhub.application.user.model.UserRole;
import org.geekhub.application.validation.UserValidation;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public CustomUserDetailsService(
        UserRepository userRepository,
        UserRoleRepository userRoleRepository
    ) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public int createUser(UserEntity userEntity) {
        if (!UserValidation.isValidEmail(userEntity.getEmail())) {
            throw new ValidationException("Invalid email!");
        }
        return userRepository.saveUser(userEntity);
    }

    public boolean isEmailExist(String email) {
        return userRepository.existsByUserEmail(email);
    }

    public void setUserRole(int userId, int roleId) {
        userRoleRepository.assignRole(userId, roleId);
    }

    public UserRole getRoleByName(String roleName) {
        return userRoleRepository.findRole(roleName)
            .orElseThrow(() -> new UserException("Role not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        return new User(
            userEntity.getEmail(),
            userEntity.getPassword(),
            mapRolesToAuthorities(
                userRoleRepository.getRoles(
                    userEntity.getId()
                )));
    }

    private List<SimpleGrantedAuthority> mapRolesToAuthorities(List<UserRole> roles) {
        return roles.stream()
            .map(role ->
                new SimpleGrantedAuthority(role.getRole())).toList();
    }

    public boolean validatePassword(String password) {
        return UserValidation.isValidPassword(password);
    }
}
