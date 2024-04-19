package org.geekhub.application.auth.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.geekhub.application.auth.dto.LoginDto;
import org.geekhub.application.auth.dto.RegisterDto;
import org.geekhub.application.user.CustomUserDetailsService;
import org.geekhub.application.user.UserEntity;
import org.geekhub.application.user.UserRole;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
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
    @Tag(name = "login-user")
    @ResponseStatus(value = HttpStatus.OK)
    public String login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.email(),
                loginDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User signed success!";
    }

    @PostMapping("/signup")
    @Tag(name = "register-new-user")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> register(
        @Valid
        @RequestBody RegisterDto registerDto,
        BindingResult bindingResult
    ) {
        if (customUserDetailsService.isEmailExist(registerDto.email())) {
            return new ResponseEntity<>("Email is taken!", HttpStatus.CONFLICT);
        }

        if (!registerDto.password().equals(registerDto.confirmPassword())) {
            return new ResponseEntity<>("Failed to confirm password", HttpStatus.BAD_REQUEST);
        }

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", ")), HttpStatus.BAD_REQUEST);
        }

        UserRole userRole = customUserDetailsService.getRoleByName("USER");
        UserEntity userEntity = new UserEntity(
            registerDto.email().trim(),
            passwordEncoder.encode(registerDto.password().trim()),
            List.of(
                new UserRole(userRole.getId(), userRole.getRole()))
            );

        int userId = customUserDetailsService.createUser(userEntity);
        customUserDetailsService.setUserRole(userId, userRole.getId());
        return new ResponseEntity<>("User registered success!", HttpStatus.CREATED);
    }
}
