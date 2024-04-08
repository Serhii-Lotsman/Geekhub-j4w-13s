package org.geekhub.api.controller.user.rest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.geekhub.api.controller.user.RegisterDto;
import org.geekhub.repository.user.UserEntity;
import org.geekhub.repository.user.UserRepository;
import org.geekhub.repository.user.UserRole;
import org.geekhub.repository.user.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
        UserRepository userRepository,
        UserRoleRepository userRoleRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterDto registerDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registerDto.email());
        userEntity.setPassword(passwordEncoder.encode((registerDto.password())));

        UserRole role = userRoleRepository.findRoleByName("USER");
        userEntity.setRoles(Collections.singletonList(role));
        userRepository.saveUser(userEntity);
    }
}
