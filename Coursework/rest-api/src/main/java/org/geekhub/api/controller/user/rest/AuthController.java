package org.geekhub.api.controller.user.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.api.controller.user.dto.LoginDto;
import org.geekhub.api.controller.user.dto.RegisterDto;
import org.geekhub.repository.user.UserEntity;
import org.geekhub.repository.user.UserRepository;
import org.geekhub.repository.user.UserRole;
import org.geekhub.repository.user.UserRoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthController(
        UserRepository userRepository,
        UserRoleRepository userRoleRepository,
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager
    ) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    @Tag(name = "login-user")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.email(),
                loginDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed success!", HttpStatus.OK);
    }

    @PostMapping("/register")
    @Tag(name = "register-new-user")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.email())) {
            return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = fromDto(registerDto);
        int userId = userRepository.saveUser(userEntity);
        userRoleRepository.assignRole(userId, userEntity.getRoles().get(0).getId());
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

    private UserEntity fromDto(RegisterDto registerDto) {
        return new UserEntity(
            registerDto.email(),
            passwordEncoder.encode(registerDto.password()),
            List.of(new UserRole(
                userRoleRepository.findRole("USER").getId(),
                userRoleRepository.findRole("USER").getRole()
            ))
        );
    }

    private RegisterDto toDto(UserEntity userEntity) {
        return new RegisterDto(
            userEntity.getEmail(),
            passwordEncoder.encode(userEntity.getPassword())
        );
    }
}
