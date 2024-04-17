package org.geekhub.application.converter;

import org.geekhub.application.auth.dto.RegisterDto;
import org.geekhub.application.user.UserService;
import org.geekhub.application.user.UserEntity;
import org.geekhub.application.user.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegisterDtoConverter {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public RegisterDtoConverter(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public UserEntity registerFromDto(RegisterDto registerDto) {
        return new UserEntity(
            registerDto.email().trim(),
            passwordEncoder.encode(registerDto.password().trim()),
            List.of(new UserRole(
                userService.getRoleByName("USER").getId(),
                userService.getRoleByName("USER").getRole()
            ))
        );
    }
}
