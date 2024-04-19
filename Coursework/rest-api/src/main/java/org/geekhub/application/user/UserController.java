package org.geekhub.application.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin-panel")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/user-role/{userId}")
    @Tag(name = "update-user-role")
    @ResponseStatus(HttpStatus.OK)
    public void editUserRole(@PathVariable Long userId, @RequestParam Long roleId) {
        userService.updateRole(userId, roleId);
    }

    @PostMapping("/{userId}")
    @Tag(name = "update-user")
    @ResponseStatus(HttpStatus.OK)
    public void editUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        UserEntity userEntity = userService.findById(userId);
        userEntity.setEmail(userDto.email());
        userEntity.setPassword(passwordEncoder.encode(userDto.password()));
        userService.updateUser(userEntity);
    }

    @DeleteMapping("/{userId}")
    @Tag(name = "delete-user")
    @ResponseStatus(HttpStatus.OK)
    public long deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}
