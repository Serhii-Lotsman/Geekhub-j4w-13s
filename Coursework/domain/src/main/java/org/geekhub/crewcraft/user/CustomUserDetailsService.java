package org.geekhub.crewcraft.user;

import org.geekhub.repository.user.UserEntity;
import org.geekhub.repository.user.UserRepository;
import org.geekhub.repository.user.UserRole;
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

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        return new User(
            userEntity.getEmail(),
            userEntity.getPassword(),
            mapRolesToAuthorities(userEntity.getRoles())
        );
    }

    private List<SimpleGrantedAuthority> mapRolesToAuthorities(List<UserRole> roles) {
        return roles.stream()
            .map(role ->
                new SimpleGrantedAuthority(role.getRole())).toList();
    }
}
