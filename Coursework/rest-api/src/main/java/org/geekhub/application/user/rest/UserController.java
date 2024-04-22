package org.geekhub.application.user.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.application.enums.Role;
import org.geekhub.application.user.dto.UserDto;
import org.geekhub.application.user.model.UserEntity;
import org.geekhub.application.user.UserService;
import org.geekhub.application.validation.UserValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1/admin-panel")
@Tag(name = "user-manage")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PutMapping("/user-role/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void editUserRole(@PathVariable Long userId, @RequestParam Role role) {
        long roleId = userService.getRoleIdByName(role.name());
        userService.updateRole(userId, roleId);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> editUser(
        @PathVariable Long userId,
        @RequestParam String email,
        @RequestParam String password
    ) {
        if (!UserValidation.isValidEmail(email)) {
            return new ResponseEntity<>("Invalid email!", HttpStatus.BAD_REQUEST);
        }
        if (!userService.validatePassword(password)) {
            return new ResponseEntity<>(
                "Password must be in range 6 - 30 and use valid symbols",
                HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = userService.findById(userId);
        userEntity.setEmail(email);
        userEntity.setPassword(passwordEncoder.encode(password));
        userService.updateUser(userEntity);
        return new ResponseEntity<>("User successfully updated!", HttpStatus.OK);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers() {
        return userService.getUsers().stream()
            .map(userEntity -> new UserDto(userEntity.getId(), userEntity.getEmail()))
            .toList();
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public long deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}
