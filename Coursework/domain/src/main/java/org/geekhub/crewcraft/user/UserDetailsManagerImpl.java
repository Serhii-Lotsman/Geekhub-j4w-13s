package org.geekhub.crewcraft.user;

import org.geekhub.repository.user.UserEntity;
import org.geekhub.repository.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsManagerImpl implements UserDetailsManager {

    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public UserDetailsManagerImpl(UserRepository userRepository, CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void createUser(UserEntity userEntity) {
        userRepository.saveUser(userEntity);
    }

    @Override
    public void updateUser(UserEntity user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return customUserDetailsService.loadUserByUsername(email);
    }
}
