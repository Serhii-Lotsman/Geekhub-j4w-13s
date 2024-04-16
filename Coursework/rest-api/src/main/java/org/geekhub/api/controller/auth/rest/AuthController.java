package org.geekhub.api.controller.auth.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.geekhub.api.controller.employeeCard.converter.EmployeeCardConverter;
import org.geekhub.api.controller.auth.dto.LoginDto;
import org.geekhub.api.controller.auth.dto.RegisterDto;
import org.geekhub.crewcraft.employeeCard.EmployeeCardService;
import org.geekhub.crewcraft.user.UserService;
import org.geekhub.repository.user.UserEntity;
import org.geekhub.repository.user.UserRole;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final EmployeeCardService employeeCardService;

    public AuthController(
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager,
        UserService userService,
        EmployeeCardService employeeCardService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.employeeCardService = employeeCardService;
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
    public ResponseEntity<String> register(
        @Valid
        @RequestBody RegisterDto registerDto,
        BindingResult bindingResult
    ) {
        if (userService.isEmailExist(registerDto.email())) {
            return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", ")), HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = fromDto(registerDto);
        int userId = userService.createUser(userEntity);
        userService.setUserRole(userId, userEntity.getRoles().get(0).getId());
        employeeCardService.saveEmployee(EmployeeCardConverter.employeeFromDto(registerDto));
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

    private UserEntity fromDto(RegisterDto registerDto) {
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
