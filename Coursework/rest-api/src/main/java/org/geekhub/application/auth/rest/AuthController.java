package org.geekhub.application.auth.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.geekhub.application.converter.EmployeeCardConverter;
import org.geekhub.application.auth.dto.LoginDto;
import org.geekhub.application.auth.dto.RegisterDto;
import org.geekhub.application.converter.RegisterDtoConverter;
import org.geekhub.application.employeeCard.EmployeeCardService;
import org.geekhub.application.user.UserService;
import org.geekhub.application.user.UserEntity;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final EmployeeCardService employeeCardService;
    private final RegisterDtoConverter registerDtoConverter;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserService userService,
            EmployeeCardService employeeCardService,
            RegisterDtoConverter registerDtoConverter
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.employeeCardService = employeeCardService;
        this.registerDtoConverter = registerDtoConverter;
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

        UserEntity userEntity = registerDtoConverter.registerFromDto(registerDto);
        int userId = userService.createUser(userEntity);
        userService.setUserRole(userId, userEntity.getRoles().get(0).getId());
        employeeCardService.saveEmployee(EmployeeCardConverter.employeeFromDto(registerDto));
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
}
