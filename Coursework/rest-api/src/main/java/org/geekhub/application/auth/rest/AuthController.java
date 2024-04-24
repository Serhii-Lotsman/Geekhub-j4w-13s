package org.geekhub.application.auth.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.application.auth.dto.LoginDto;
import org.geekhub.application.auth.dto.RegisterDto;
import org.geekhub.application.enums.Role;
import org.geekhub.application.exception.ValidationException;
import org.geekhub.application.user.CustomUserDetailsService;
import org.geekhub.application.user.model.UserEntity;
import org.geekhub.application.user.model.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "user-auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
        AuthenticationManager authenticationManager,
        CustomUserDetailsService customUserDetailsService,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signin")
    @ResponseStatus(reason = "Sign In successful", value = HttpStatus.OK)
    public void login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.email(),
                loginDto.password()
            ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @PostMapping("/signup")
    @ResponseStatus(reason = "Sign Up succeed", value = HttpStatus.CREATED)
    public void register(@RequestBody RegisterDto registerDto) {
        if (!customUserDetailsService.validatePassword(registerDto.password())) {
            throw new ValidationException("Invalid password!");
        }

        if (!registerDto.password().equals(registerDto.confirmPassword())) {
            throw new ValidationException("Failed to confirm password!");
        }

        UserRole userRole = customUserDetailsService.getRoleByName(Role.USER.name());
        UserEntity userEntity = new UserEntity(
            registerDto.email().trim(),
            passwordEncoder.encode(registerDto.password().trim()),
            List.of(
                new UserRole(userRole.getId(), userRole.getRole()))
        );

        int userId = customUserDetailsService.createUser(userEntity);
        customUserDetailsService.setUserRole(userId, userRole.getId());
    }
}
