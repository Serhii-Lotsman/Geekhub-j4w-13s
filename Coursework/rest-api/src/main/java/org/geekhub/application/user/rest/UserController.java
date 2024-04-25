package org.geekhub.application.user.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.application.exception.ValidationException;
import org.geekhub.application.user.dto.UserDto;
import org.geekhub.application.user.model.UserEntity;
import org.geekhub.application.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hr-panel")
@Tag(name = "manage-users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers() {
        return userService.getUsers().stream()
            .map(userEntity -> new UserDto(userEntity.getId(), userEntity.getEmail()))
            .toList();
    }

    @PutMapping("/{userId}")
    @ResponseStatus(reason = "User updated", value = HttpStatus.OK)
    public void updateUser(
        @PathVariable long userId,
        @RequestParam String email,
        @RequestParam String password
    ) {
        if (!userService.validatePassword(password)) {
            throw new ValidationException("Password must be in range 6 - 30 and use valid symbols");
        }

        UserEntity userEntity = userService.findById(userId);
        userEntity.setEmail(email.trim());
        userEntity.setPassword(passwordEncoder.encode(password));
        userService.updateUser(userEntity);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(reason = "User deleted", value = HttpStatus.NO_CONTENT)
    public long deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}
