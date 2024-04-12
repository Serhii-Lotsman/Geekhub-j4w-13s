package org.geekhub.api.controller.user.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.api.controller.user.RegisterDto;
import org.geekhub.crewcraft.user.UserDetailsManager;
import org.geekhub.crewcraft.user.UserDetailsManagerImpl;
import org.geekhub.repository.user.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserDetailsManagerImpl userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @Tag(name = "register-new-user")
    public void register(@RequestBody RegisterDto registerDto) {
        UserEntity userEntity = fromDto(registerDto);
        userDetailsManager.createUser(userEntity);
    }

    private UserEntity fromDto(RegisterDto registerDto) {
        return new UserEntity(
            registerDto.email(),
            passwordEncoder.encode(registerDto.password()),
            registerDto.enabled()
        );
    }

    private RegisterDto toDto(UserEntity userEntity) {
        return new RegisterDto(
            userEntity.getEmail(),
            passwordEncoder.encode(userEntity.getPassword()),
            userEntity.isEnabled()
        );
    }
}
